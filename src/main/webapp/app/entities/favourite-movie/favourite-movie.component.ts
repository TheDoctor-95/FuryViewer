import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { FavouriteMovie } from './favourite-movie.model';
import { FavouriteMovieService } from './favourite-movie.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-favourite-movie',
    templateUrl: './favourite-movie.component.html'
})
export class FavouriteMovieComponent implements OnInit, OnDestroy {
favouriteMovies: FavouriteMovie[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private favouriteMovieService: FavouriteMovieService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.favouriteMovieService.query().subscribe(
            (res: ResponseWrapper) => {
                this.favouriteMovies = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFavouriteMovies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FavouriteMovie) {
        return item.id;
    }
    registerChangeInFavouriteMovies() {
        this.eventSubscriber = this.eventManager.subscribe('favouriteMovieListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
