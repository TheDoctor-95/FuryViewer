import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FavouriteSeries } from './favourite-series.model';
import { FavouriteSeriesService } from './favourite-series.service';

@Component({
    selector: 'jhi-favourite-series-detail',
    templateUrl: './favourite-series-detail.component.html'
})
export class FavouriteSeriesDetailComponent implements OnInit, OnDestroy {

    favouriteSeries: FavouriteSeries;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private favouriteSeriesService: FavouriteSeriesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFavouriteSeries();
    }

    load(id) {
        this.favouriteSeriesService.find(id).subscribe((favouriteSeries) => {
            this.favouriteSeries = favouriteSeries;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFavouriteSeries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'favouriteSeriesListModification',
            (response) => this.load(this.favouriteSeries.id)
        );
    }
}
