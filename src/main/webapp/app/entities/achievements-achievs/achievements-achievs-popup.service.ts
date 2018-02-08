import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AchievementsAchievs } from './achievements-achievs.model';
import { AchievementsAchievsService } from './achievements-achievs.service';

@Injectable()
export class AchievementsAchievsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private achievementsAchievsService: AchievementsAchievsService

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
                this.achievementsAchievsService.find(id).subscribe((achievementsAchievs) => {
                    achievementsAchievs.date = this.datePipe
                        .transform(achievementsAchievs.date, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.achievementsAchievsModalRef(component, achievementsAchievs);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.achievementsAchievsModalRef(component, new AchievementsAchievs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    achievementsAchievsModalRef(component: Component, achievementsAchievs: AchievementsAchievs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.achievementsAchievs = achievementsAchievs;
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
