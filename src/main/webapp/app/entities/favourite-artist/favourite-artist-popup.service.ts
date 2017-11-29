import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { FavouriteArtist } from './favourite-artist.model';
import { FavouriteArtistService } from './favourite-artist.service';

@Injectable()
export class FavouriteArtistPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private favouriteArtistService: FavouriteArtistService

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
                this.favouriteArtistService.find(id).subscribe((favouriteArtist) => {
                    favouriteArtist.date = this.datePipe
                        .transform(favouriteArtist.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.favouriteArtistModalRef(component, favouriteArtist);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.favouriteArtistModalRef(component, new FavouriteArtist());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    favouriteArtistModalRef(component: Component, favouriteArtist: FavouriteArtist): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.favouriteArtist = favouriteArtist;
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
