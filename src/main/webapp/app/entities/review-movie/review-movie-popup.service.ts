import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ReviewMovie } from './review-movie.model';
import { ReviewMovieService } from './review-movie.service';

@Injectable()
export class ReviewMoviePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private reviewMovieService: ReviewMovieService

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
                this.reviewMovieService.find(id).subscribe((reviewMovie) => {
                    reviewMovie.date = this.datePipe
                        .transform(reviewMovie.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.reviewMovieModalRef(component, reviewMovie);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.reviewMovieModalRef(component, new ReviewMovie());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    reviewMovieModalRef(component: Component, reviewMovie: ReviewMovie): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.reviewMovie = reviewMovie;
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
