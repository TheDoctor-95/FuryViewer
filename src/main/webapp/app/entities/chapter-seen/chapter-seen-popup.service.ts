import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ChapterSeen } from './chapter-seen.model';
import { ChapterSeenService } from './chapter-seen.service';

@Injectable()
export class ChapterSeenPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private chapterSeenService: ChapterSeenService

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
                this.chapterSeenService.find(id).subscribe((chapterSeen) => {
                    chapterSeen.date = this.datePipe
                        .transform(chapterSeen.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.chapterSeenModalRef(component, chapterSeen);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.chapterSeenModalRef(component, new ChapterSeen());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    chapterSeenModalRef(component: Component, chapterSeen: ChapterSeen): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.chapterSeen = chapterSeen;
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
