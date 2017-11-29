import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { HatredSeries } from './hatred-series.model';
import { HatredSeriesService } from './hatred-series.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-hatred-series',
    templateUrl: './hatred-series.component.html'
})
export class HatredSeriesComponent implements OnInit, OnDestroy {
hatredSeries: HatredSeries[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private hatredSeriesService: HatredSeriesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.hatredSeriesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.hatredSeries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHatredSeries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HatredSeries) {
        return item.id;
    }
    registerChangeInHatredSeries() {
        this.eventSubscriber = this.eventManager.subscribe('hatredSeriesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
