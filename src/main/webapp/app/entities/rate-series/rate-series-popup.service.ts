import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RateSeries } from './rate-series.model';
import { RateSeriesService } from './rate-series.service';

@Injectable()
export class RateSeriesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rateSeriesService: RateSeriesService

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
                this.rateSeriesService.find(id).subscribe((rateSeries) => {
                    rateSeries.date = this.datePipe
                        .transform(rateSeries.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.rateSeriesModalRef(component, rateSeries);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rateSeriesModalRef(component, new RateSeries());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rateSeriesModalRef(component: Component, rateSeries: RateSeries): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.rateSeries = rateSeries;
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
