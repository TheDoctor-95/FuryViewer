import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SeriesStats } from './series-stats.model';
import { SeriesStatsService } from './series-stats.service';

@Component({
    selector: 'jhi-series-stats-detail',
    templateUrl: './series-stats-detail.component.html'
})
export class SeriesStatsDetailComponent implements OnInit, OnDestroy {

    seriesStats: SeriesStats;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private seriesStatsService: SeriesStatsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSeriesStats();
    }

    load(id) {
        this.seriesStatsService.find(id).subscribe((seriesStats) => {
            this.seriesStats = seriesStats;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSeriesStats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'seriesStatsListModification',
            (response) => this.load(this.seriesStats.id)
        );
    }
}
