import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { HatredMovie } from './hatred-movie.model';
import { HatredMovieService } from './hatred-movie.service';

@Injectable()
export class HatredMoviePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private hatredMovieService: HatredMovieService

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
                this.hatredMovieService.find(id).subscribe((hatredMovie) => {
                    hatredMovie.date = this.datePipe
                        .transform(hatredMovie.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.hatredMovieModalRef(component, hatredMovie);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.hatredMovieModalRef(component, new HatredMovie());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    hatredMovieModalRef(component: Component, hatredMovie: HatredMovie): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.hatredMovie = hatredMovie;
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
