import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { FavouriteMovie } from './favourite-movie.model';
import { FavouriteMovieService } from './favourite-movie.service';

@Injectable()
export class FavouriteMoviePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private favouriteMovieService: FavouriteMovieService

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
                this.favouriteMovieService.find(id).subscribe((favouriteMovie) => {
                    favouriteMovie.date = this.datePipe
                        .transform(favouriteMovie.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.favouriteMovieModalRef(component, favouriteMovie);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.favouriteMovieModalRef(component, new FavouriteMovie());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    favouriteMovieModalRef(component: Component, favouriteMovie: FavouriteMovie): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.favouriteMovie = favouriteMovie;
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
