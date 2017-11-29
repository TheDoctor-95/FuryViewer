import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Season } from './season.model';
import { SeasonService } from './season.service';

@Injectable()
export class SeasonPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private seasonService: SeasonService

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
                this.seasonService.find(id).subscribe((season) => {
                    if (season.releaseDate) {
                        season.releaseDate = {
                            year: season.releaseDate.getFullYear(),
                            month: season.releaseDate.getMonth() + 1,
                            day: season.releaseDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.seasonModalRef(component, season);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.seasonModalRef(component, new Season());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    seasonModalRef(component: Component, season: Season): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.season = season;
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
