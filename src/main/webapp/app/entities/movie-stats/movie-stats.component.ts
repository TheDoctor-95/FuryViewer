import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { MovieStats } from './movie-stats.model';
import { MovieStatsService } from './movie-stats.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-movie-stats',
    templateUrl: './movie-stats.component.html'
})
export class MovieStatsComponent implements OnInit, OnDestroy {
movieStats: MovieStats[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private movieStatsService: MovieStatsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.movieStatsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.movieStats = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMovieStats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MovieStats) {
        return item.id;
    }
    registerChangeInMovieStats() {
        this.eventSubscriber = this.eventManager.subscribe('movieStatsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
