import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { FavouriteSeries } from './favourite-series.model';
import { FavouriteSeriesService } from './favourite-series.service';

@Injectable()
export class FavouriteSeriesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private favouriteSeriesService: FavouriteSeriesService

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
                this.favouriteSeriesService.find(id).subscribe((favouriteSeries) => {
                    favouriteSeries.date = this.datePipe
                        .transform(favouriteSeries.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.favouriteSeriesModalRef(component, favouriteSeries);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.favouriteSeriesModalRef(component, new FavouriteSeries());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    favouriteSeriesModalRef(component: Component, favouriteSeries: FavouriteSeries): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.favouriteSeries = favouriteSeries;
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
