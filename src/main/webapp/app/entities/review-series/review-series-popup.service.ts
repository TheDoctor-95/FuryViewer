import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ReviewSeries } from './review-series.model';
import { ReviewSeriesService } from './review-series.service';

@Injectable()
export class ReviewSeriesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private reviewSeriesService: ReviewSeriesService

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
                this.reviewSeriesService.find(id).subscribe((reviewSeries) => {
                    reviewSeries.date = this.datePipe
                        .transform(reviewSeries.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.reviewSeriesModalRef(component, reviewSeries);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.reviewSeriesModalRef(component, new ReviewSeries());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    reviewSeriesModalRef(component: Component, reviewSeries: ReviewSeries): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.reviewSeries = reviewSeries;
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
