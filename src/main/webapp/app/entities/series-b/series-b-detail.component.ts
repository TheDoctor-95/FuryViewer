import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SeriesB } from './series-b.model';
import { SeriesBService } from './series-b.service';

@Component({
    selector: 'jhi-series-b-detail',
    templateUrl: './series-b-detail.component.html'
})
export class SeriesBDetailComponent implements OnInit, OnDestroy {

    seriesB: SeriesB;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private seriesBService: SeriesBService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSeriesBS();
    }

    load(id) {
        this.seriesBService.find(id).subscribe((seriesB) => {
            this.seriesB = seriesB;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSeriesBS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'seriesBListModification',
            (response) => this.load(this.seriesB.id)
        );
    }
}
