import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ReviewSeries } from './review-series.model';
import { ReviewSeriesService } from './review-series.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-review-series',
    templateUrl: './review-series.component.html'
})
export class ReviewSeriesComponent implements OnInit, OnDestroy {
reviewSeries: ReviewSeries[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private reviewSeriesService: ReviewSeriesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.reviewSeriesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.reviewSeries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInReviewSeries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ReviewSeries) {
        return item.id;
    }
    registerChangeInReviewSeries() {
        this.eventSubscriber = this.eventManager.subscribe('reviewSeriesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
