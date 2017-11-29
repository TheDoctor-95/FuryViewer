import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { FavouriteSeries } from './favourite-series.model';
import { FavouriteSeriesService } from './favourite-series.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-favourite-series',
    templateUrl: './favourite-series.component.html'
})
export class FavouriteSeriesComponent implements OnInit, OnDestroy {
favouriteSeries: FavouriteSeries[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private favouriteSeriesService: FavouriteSeriesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.favouriteSeriesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.favouriteSeries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFavouriteSeries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FavouriteSeries) {
        return item.id;
    }
    registerChangeInFavouriteSeries() {
        this.eventSubscriber = this.eventManager.subscribe('favouriteSeriesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
