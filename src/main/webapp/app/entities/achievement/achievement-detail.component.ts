import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Achievement } from './achievement.model';
import { AchievementService } from './achievement.service';

@Component({
    selector: 'jhi-achievement-detail',
    templateUrl: './achievement-detail.component.html'
})
export class AchievementDetailComponent implements OnInit, OnDestroy {

    achievement: Achievement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private achievementService: AchievementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAchievements();
    }

    load(id) {
        this.achievementService.find(id).subscribe((achievement) => {
            this.achievement = achievement;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAchievements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'achievementListModification',
            (response) => this.load(this.achievement.id)
        );
    }
}
