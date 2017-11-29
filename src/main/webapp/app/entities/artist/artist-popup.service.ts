import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Artist } from './artist.model';
import { ArtistService } from './artist.service';

@Injectable()
export class ArtistPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private artistService: ArtistService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.artistService.find(id).subscribe((artist) => {
                    if (artist.birthdate) {
                        artist.birthdate = {
                            year: artist.birthdate.getFullYear(),
                            month: artist.birthdate.getMonth() + 1,
                            day: artist.birthdate.getDate()
                        };
                    }
                    if (artist.deathdate) {
                        artist.deathdate = {
                            year: artist.deathdate.getFullYear(),
                            month: artist.deathdate.getMonth() + 1,
                            day: artist.deathdate.getDate()
                        };
                    }
                    this.ngbModalRef = this.artistModalRef(component, artist);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.artistModalRef(component, new Artist());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    artistModalRef(component: Component, artist: Artist): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.artist = artist;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
