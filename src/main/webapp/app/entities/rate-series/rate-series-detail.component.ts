import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RateSeries } from './rate-series.model';
import { RateSeriesService } from './rate-series.service';

@Component({
    selector: 'jhi-rate-series-detail',
    templateUrl: './rate-series-detail.component.html'
})
export class RateSeriesDetailComponent implements OnInit, OnDestroy {

    rateSeries: RateSeries;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rateSeriesService: RateSeriesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRateSeries();
    }

    load(id) {
        this.rateSeriesService.find(id).subscribe((rateSeries) => {
            this.rateSeries = rateSeries;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRateSeries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'rateSeriesListModification',
            (response) => this.load(this.rateSeries.id)
        );
    }
}
