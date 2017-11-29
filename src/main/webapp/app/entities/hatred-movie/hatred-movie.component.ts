import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { HatredMovie } from './hatred-movie.model';
import { HatredMovieService } from './hatred-movie.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-hatred-movie',
    templateUrl: './hatred-movie.component.html'
})
export class HatredMovieComponent implements OnInit, OnDestroy {
hatredMovies: HatredMovie[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private hatredMovieService: HatredMovieService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.hatredMovieService.query().subscribe(
            (res: ResponseWrapper) => {
                this.hatredMovies = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHatredMovies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HatredMovie) {
        return item.id;
    }
    registerChangeInHatredMovies() {
        this.eventSubscriber = this.eventManager.subscribe('hatredMovieListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
