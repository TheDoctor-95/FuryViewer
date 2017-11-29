import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HatredSeries } from './hatred-series.model';
import { HatredSeriesService } from './hatred-series.service';

@Component({
    selector: 'jhi-hatred-series-detail',
    templateUrl: './hatred-series-detail.component.html'
})
export class HatredSeriesDetailComponent implements OnInit, OnDestroy {

    hatredSeries: HatredSeries;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hatredSeriesService: HatredSeriesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHatredSeries();
    }

    load(id) {
        this.hatredSeriesService.find(id).subscribe((hatredSeries) => {
            this.hatredSeries = hatredSeries;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHatredSeries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hatredSeriesListModification',
            (response) => this.load(this.hatredSeries.id)
        );
    }
}
