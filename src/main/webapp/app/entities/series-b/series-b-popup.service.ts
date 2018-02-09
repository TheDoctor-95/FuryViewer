import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SeriesB } from './series-b.model';
import { SeriesBService } from './series-b.service';

@Injectable()
export class SeriesBPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private seriesBService: SeriesBService

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
                this.seriesBService.find(id).subscribe((seriesB) => {
                    if (seriesB.release_date) {
                        seriesB.release_date = {
                            year: seriesB.release_date.getFullYear(),
                            month: seriesB.release_date.getMonth() + 1,
                            day: seriesB.release_date.getDate()
                        };
                    }
                    this.ngbModalRef = this.seriesBModalRef(component, seriesB);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.seriesBModalRef(component, new SeriesB());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    seriesBModalRef(component: Component, seriesB: SeriesB): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.seriesB = seriesB;
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
