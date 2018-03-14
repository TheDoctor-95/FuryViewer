import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Series } from './series.model';
import { SeriesService } from './series.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';


@Component({
    selector: 'jhi-series',
    templateUrl: './series.component.html',
    providers: [NgbRatingConfig],
    styles: [`
    .star {
      font-size: 1.5rem;
      color: #b0c4de;
    }
    .filled {
      color: #1e90ff;
    }
    .bad {
      color: #deb0b0;
    }
    .filled.bad {
      color: #ff1e1e;
    }
  `]
})
export class SeriesComponent implements OnInit, OnDestroy {
series: Series[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private seriesService: SeriesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.seriesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.series = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSeries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Series) {
        return item.id;
    }
    registerChangeInSeries() {
        this.eventSubscriber = this.eventManager.subscribe('seriesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
