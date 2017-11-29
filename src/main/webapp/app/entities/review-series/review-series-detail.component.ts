import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ReviewSeries } from './review-series.model';
import { ReviewSeriesService } from './review-series.service';

@Component({
    selector: 'jhi-review-series-detail',
    templateUrl: './review-series-detail.component.html'
})
export class ReviewSeriesDetailComponent implements OnInit, OnDestroy {

    reviewSeries: ReviewSeries;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private reviewSeriesService: ReviewSeriesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReviewSeries();
    }

    load(id) {
        this.reviewSeriesService.find(id).subscribe((reviewSeries) => {
            this.reviewSeries = reviewSeries;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReviewSeries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'reviewSeriesListModification',
            (response) => this.load(this.reviewSeries.id)
        );
    }
}
