import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { RateMovie } from './rate-movie.model';
import { RateMovieService } from './rate-movie.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-rate-movie',
    templateUrl: './rate-movie.component.html'
})
export class RateMovieComponent implements OnInit, OnDestroy {
rateMovies: RateMovie[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rateMovieService: RateMovieService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.rateMovieService.query().subscribe(
            (res: ResponseWrapper) => {
                this.rateMovies = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRateMovies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RateMovie) {
        return item.id;
    }
    registerChangeInRateMovies() {
        this.eventSubscriber = this.eventManager.subscribe('rateMovieListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
