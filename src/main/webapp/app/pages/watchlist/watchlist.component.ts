import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Watchlist } from './watchlist.model';
import { WatchlistService } from './watchlist.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import {FilmographyArtistModel} from "../../shared/model/filmographyArtist.model";
import {Globals} from '../../shared/globals';

@Component({
    selector: 'jhi-watchlist',
    templateUrl: './watchlist.component.html'
})
export class WatchlistComponent implements OnInit, OnDestroy {

    watchlists: Watchlist[];
    totalItems;
    parseLinks;
    page;
    itemsPerPage;
    sort;
    links;
    reset;
    currentAccount: any;
    eventSubscriber: Subscription;
    filmography: FilmographyArtistModel[];
    constructor(
        private watchlistService: WatchlistService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private router: Router,
        public globals: Globals
    ) {

    }

    loadAll() {
            this.watchlistService.query({
                page: this.page,
                size: this.itemsPerPage,
            }).subscribe(
                (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
                (res: ResponseWrapper) => this.onError(res.json)
            );
    }

    ngOnInit() {

        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.cargar();

        this.registerChangeInWatchlists();
    }
    option(option: string) {
        this.globals.opcio = option;
        this.cargar();
    }
    change(multimedia: string) {
        this.globals.multimedia = multimedia;
        if (this.globals.multimedia === 'movie') {
            if ('following' === this.globals.opcio) {
    this.globals.multimedia = 'pending';
}
        }

        this.cargar();
    }

    cargar() {
        console.log('Cargando Info ' + this.globals.multimedia + ' ' + this.globals.opcio);
        this.watchlistService.load(this.globals.multimedia, this.globals.opcio).subscribe(
                (res: ResponseWrapper) => {
                    this.filmography = res.json;
                    console.log(this.filmography);
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );

    }

    goTo(type: String, id: number) {
        switch (type) {
            case 'series' : {
                this.router.navigate(['/series', id]);
                break;
            }
            case 'movie' : {
                this.router.navigate(['/movie', id]);
                break;
            }
        }
    }


    ngOnDestroy() {

        this.eventManager.destroy(this.eventSubscriber);
    }
    registerChangeInWatchlists() {
        this.eventSubscriber = this.eventManager.subscribe('watchlistListModification', (response) => this.reset());
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.watchlists.push(data[i]);
        }
    }    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
