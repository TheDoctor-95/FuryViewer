import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { HatredArtist } from './hatred-artist.model';
import { HatredArtistService } from './hatred-artist.service';

@Injectable()
export class HatredArtistPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private hatredArtistService: HatredArtistService

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
                this.hatredArtistService.find(id).subscribe((hatredArtist) => {
                    hatredArtist.date = this.datePipe
                        .transform(hatredArtist.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.hatredArtistModalRef(component, hatredArtist);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.hatredArtistModalRef(component, new HatredArtist());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    hatredArtistModalRef(component: Component, hatredArtist: HatredArtist): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.hatredArtist = hatredArtist;
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
