import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Season } from './season.model';
import { SeasonService } from './season.service';

@Component({
    selector: 'jhi-season-detail',
    templateUrl: './season-detail.component.html'
})
export class SeasonDetailComponent implements OnInit, OnDestroy {

    season: Season;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private seasonService: SeasonService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSeasons();
    }

    load(id) {
        this.seasonService.find(id).subscribe((season) => {
            this.season = season;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSeasons() {
        this.eventSubscriber = this.eventManager.subscribe(
            'seasonListModification',
            (response) => this.load(this.season.id)
        );
    }
}
