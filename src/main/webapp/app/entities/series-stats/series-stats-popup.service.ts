import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { SeriesStats } from './series-stats.model';
import { SeriesStatsService } from './series-stats.service';

@Injectable()
export class SeriesStatsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private seriesStatsService: SeriesStatsService

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
                this.seriesStatsService.find(id).subscribe((seriesStats) => {
                    seriesStats.date = this.datePipe
                        .transform(seriesStats.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.seriesStatsModalRef(component, seriesStats);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.seriesStatsModalRef(component, new SeriesStats());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    seriesStatsModalRef(component: Component, seriesStats: SeriesStats): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.seriesStats = seriesStats;
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
