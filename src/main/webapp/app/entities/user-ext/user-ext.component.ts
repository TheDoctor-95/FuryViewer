import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { UserExt } from './user-ext.model';
import { UserExtService } from './user-ext.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import {CountryService} from "../country";

@Component({
    selector: 'jhi-user-ext',
    templateUrl: './user-ext.component.html'
})
export class UserExtComponent implements OnInit, OnDestroy {
userExts: UserExt[];
    currentAccount: any;
    eventSubscriber: Subscription;
    numSeriesSeen: number;
    numSeriesFollowing: number;
    numSeriesPending: number;
    numSeriesFav: number;
    numSeriesHatred: number;
    numMoviesSeen: number;
    numMoviesPending: number;
    numMoviesFav: number;
    numMoviesHatred: number;
    numArtistFav: number;
    numArtistHatred: number;
    userName: string;

    constructor(
        private userExtService: UserExtService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private homeService: CountryService
    ) {
        this.numSeriesSeen = 0;
        this.numSeriesFollowing = 0;
        this.numSeriesPending = 0;
        this.numSeriesFav = 0;
        this.numSeriesHatred = 0;
        this.numMoviesSeen = 0;
        this.numMoviesPending = 0;
        this.numMoviesFav = 0;
        this.numMoviesHatred = 0;
        this.numArtistFav = 0;
        this.numArtistHatred = 0;
        this.userName = '';
    }

    loadAll() {
        this.userExtService.query().subscribe(
            (res: ResponseWrapper) => {
                this.userExts = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.loadAbsoluteStats();
        this.loadUsername();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserExts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserExt) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInUserExts() {
        this.eventSubscriber = this.eventManager.subscribe('userExtListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    loadAbsoluteStats() {
        this.homeService.getAbsoluteStatsUser().subscribe((stats)  => {
            this.numSeriesSeen = stats.series[0];
            this.numSeriesFollowing = stats.series[1];
            this.numSeriesPending = stats.series[2];
            this.numSeriesFav = stats.series[3];
            this.numSeriesHatred = stats.series[4];
            this.numMoviesSeen = stats.movies[0];
            this.numMoviesPending = stats.movies[1];
            this.numMoviesFav = stats.movies[2];
            this.numMoviesHatred = stats.movies[3];
            this.numArtistFav = stats.artists[0];
            this.numArtistHatred = stats.artists[1];
        });
    }

    loadUsername() {
        this.userExtService.getUsername().subscribe((name) => {
            this.userName = name.url;
        })
    }
}
