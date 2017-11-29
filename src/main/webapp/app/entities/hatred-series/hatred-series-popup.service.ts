import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { HatredSeries } from './hatred-series.model';
import { HatredSeriesService } from './hatred-series.service';

@Injectable()
export class HatredSeriesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private hatredSeriesService: HatredSeriesService

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
                this.hatredSeriesService.find(id).subscribe((hatredSeries) => {
                    hatredSeries.date = this.datePipe
                        .transform(hatredSeries.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.hatredSeriesModalRef(component, hatredSeries);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.hatredSeriesModalRef(component, new HatredSeries());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    hatredSeriesModalRef(component: Component, hatredSeries: HatredSeries): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.hatredSeries = hatredSeries;
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
