import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { RateSeries } from './rate-series.model';
import { RateSeriesService } from './rate-series.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-rate-series',
    templateUrl: './rate-series.component.html'
})
export class RateSeriesComponent implements OnInit, OnDestroy {
rateSeries: RateSeries[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rateSeriesService: RateSeriesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.rateSeriesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.rateSeries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRateSeries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RateSeries) {
        return item.id;
    }
    registerChangeInRateSeries() {
        this.eventSubscriber = this.eventManager.subscribe('rateSeriesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
