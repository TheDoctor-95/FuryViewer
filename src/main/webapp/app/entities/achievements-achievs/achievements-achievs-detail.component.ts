import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AchievementsAchievs } from './achievements-achievs.model';
import { AchievementsAchievsService } from './achievements-achievs.service';

@Component({
    selector: 'jhi-achievements-achievs-detail',
    templateUrl: './achievements-achievs-detail.component.html'
})
export class AchievementsAchievsDetailComponent implements OnInit, OnDestroy {

    achievementsAchievs: AchievementsAchievs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private achievementsAchievsService: AchievementsAchievsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAchievementsAchievs();
    }

    load(id) {
        this.achievementsAchievsService.find(id).subscribe((achievementsAchievs) => {
            this.achievementsAchievs = achievementsAchievs;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAchievementsAchievs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'achievementsAchievsListModification',
            (response) => this.load(this.achievementsAchievs.id)
        );
    }
}
