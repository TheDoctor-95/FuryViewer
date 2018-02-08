import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AchievementsAchievs } from './achievements-achievs.model';
import { AchievementsAchievsPopupService } from './achievements-achievs-popup.service';
import { AchievementsAchievsService } from './achievements-achievs.service';
import { User, UserService } from '../../shared';
import { Achievement, AchievementService } from '../achievement';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-achievements-achievs-dialog',
    templateUrl: './achievements-achievs-dialog.component.html'
})
export class AchievementsAchievsDialogComponent implements OnInit {

    achievementsAchievs: AchievementsAchievs;
    isSaving: boolean;

    users: User[];

    achievements: Achievement[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private achievementsAchievsService: AchievementsAchievsService,
        private userService: UserService,
        private achievementService: AchievementService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.achievementService.query()
            .subscribe((res: ResponseWrapper) => { this.achievements = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.achievementsAchievs.id !== undefined) {
            this.subscribeToSaveResponse(
                this.achievementsAchievsService.update(this.achievementsAchievs));
        } else {
            this.subscribeToSaveResponse(
                this.achievementsAchievsService.create(this.achievementsAchievs));
        }
    }

    private subscribeToSaveResponse(result: Observable<AchievementsAchievs>) {
        result.subscribe((res: AchievementsAchievs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AchievementsAchievs) {
        this.eventManager.broadcast({ name: 'achievementsAchievsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackAchievementById(index: number, item: Achievement) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-achievements-achievs-popup',
    template: ''
})
export class AchievementsAchievsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private achievementsAchievsPopupService: AchievementsAchievsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.achievementsAchievsPopupService
                    .open(AchievementsAchievsDialogComponent as Component, params['id']);
            } else {
                this.achievementsAchievsPopupService
                    .open(AchievementsAchievsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
