import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { SeriesStats } from './series-stats.model';
import { SeriesStatsService } from './series-stats.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-series-stats',
    templateUrl: './series-stats.component.html'
})
export class SeriesStatsComponent implements OnInit, OnDestroy {
seriesStats: SeriesStats[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private seriesStatsService: SeriesStatsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.seriesStatsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.seriesStats = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSeriesStats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SeriesStats) {
        return item.id;
    }
    registerChangeInSeriesStats() {
        this.eventSubscriber = this.eventManager.subscribe('seriesStatsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
