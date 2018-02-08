import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ArtistB } from './artist-b.model';
import { ArtistBService } from './artist-b.service';

@Injectable()
export class ArtistBPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private artistBService: ArtistBService

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
                this.artistBService.find(id).subscribe((artistB) => {
                    if (artistB.birthdate) {
                        artistB.birthdate = {
                            year: artistB.birthdate.getFullYear(),
                            month: artistB.birthdate.getMonth() + 1,
                            day: artistB.birthdate.getDate()
                        };
                    }
                    if (artistB.deathdate) {
                        artistB.deathdate = {
                            year: artistB.deathdate.getFullYear(),
                            month: artistB.deathdate.getMonth() + 1,
                            day: artistB.deathdate.getDate()
                        };
                    }
                    this.ngbModalRef = this.artistBModalRef(component, artistB);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.artistBModalRef(component, new ArtistB());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    artistBModalRef(component: Component, artistB: ArtistB): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.artistB = artistB;
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
