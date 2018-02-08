import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Achievement } from './achievement.model';
import { AchievementService } from './achievement.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-achievement',
    templateUrl: './achievement.component.html'
})
export class AchievementComponent implements OnInit, OnDestroy {
achievements: Achievement[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private achievementService: AchievementService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.achievementService.query().subscribe(
            (res: ResponseWrapper) => {
                this.achievements = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAchievements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Achievement) {
        return item.id;
    }
    registerChangeInAchievements() {
        this.eventSubscriber = this.eventManager.subscribe('achievementListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
