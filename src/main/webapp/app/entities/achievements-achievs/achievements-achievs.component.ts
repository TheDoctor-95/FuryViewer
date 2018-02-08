import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { AchievementsAchievs } from './achievements-achievs.model';
import { AchievementsAchievsService } from './achievements-achievs.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-achievements-achievs',
    templateUrl: './achievements-achievs.component.html'
})
export class AchievementsAchievsComponent implements OnInit, OnDestroy {
achievementsAchievs: AchievementsAchievs[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private achievementsAchievsService: AchievementsAchievsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.achievementsAchievsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.achievementsAchievs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAchievementsAchievs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AchievementsAchievs) {
        return item.id;
    }
    registerChangeInAchievementsAchievs() {
        this.eventSubscriber = this.eventManager.subscribe('achievementsAchievsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
