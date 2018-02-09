import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { SeriesB } from './series-b.model';
import { SeriesBService } from './series-b.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-series-b',
    templateUrl: './series-b.component.html'
})
export class SeriesBComponent implements OnInit, OnDestroy {
seriesBS: SeriesB[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private seriesBService: SeriesBService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.seriesBService.query().subscribe(
            (res: ResponseWrapper) => {
                this.seriesBS = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSeriesBS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SeriesB) {
        return item.id;
    }
    registerChangeInSeriesBS() {
        this.eventSubscriber = this.eventManager.subscribe('seriesBListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
