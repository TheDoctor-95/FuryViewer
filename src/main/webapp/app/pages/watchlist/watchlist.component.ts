import {Component, OnInit, OnDestroy, HostListener} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager, JhiParseLinks, JhiAlertService} from 'ng-jhipster';

import {Watchlist} from './watchlist.model';
import {WatchlistService} from './watchlist.service';
import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../../shared';
import {FilmographyArtistModel} from '../../shared/model/filmographyArtist.model';
import {Globals} from '../../shared/globals';
import {Title} from '@angular/platform-browser';

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
    loading: boolean;
    pagenumber = 0;
    toShow: number = 0;

    constructor(private watchlistService: WatchlistService,
                private jhiAlertService: JhiAlertService,
                private eventManager: JhiEventManager,
                private principal: Principal,
                private router: Router,
                public globals: Globals,
                private titleService: Title) {
        this.onWindowScroll = this.onWindowScroll.bind(this);
    }

    onWindowScroll(): void {
        console.log('scrolling!');
        console.log(this.toShow, this.filmography.length);
        console.log(this.filmography);
        if (( document.getElementsByClassName('ng-sidebar__content ng-sidebar__content--animate')[0].clientHeight
            + document.getElementsByClassName('ng-sidebar__content ng-sidebar__content--animate')[0].scrollTop) ===
            document.getElementsByClassName('ng-sidebar__content ng-sidebar__content--animate')[0].scrollHeight
            && !this.loading && this.toShow !== this.filmography.length) {
            this.pagenumber = this.pagenumber + 1;
            this.load()
        }
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
        window.addEventListener('scroll', this.onWindowScroll, true);
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.load();
        this.registerChangeInWatchlists();
        this.loadToShow();
        this.titleService.setTitle("Watchlist - FuryViewer");
        if (this.globals.multimedia === 'artist'){
            this.globals.multimedia = 'movie';
        }
    }

    option(option: string) {
        this.globals.opcio = option;
        this.pagenumber = 0;
        this.loadToShow();
        this.load();
    }

    change(multimedia: string) {
        this.pagenumber = 0;
        this.globals.multimedia = multimedia;
        if (this.globals.multimedia === 'movie') {
            if ('following' === this.globals.opcio) {
                this.globals.opcio = 'pending';
            }
        }
        this.loadToShow();
        this.load();
    }

    load(): void {
        this.loading = true;
        console.log('Cargando Info ' + this.globals.multimedia + ' ' + this.globals.opcio);
        this.watchlistService.load(this.globals.multimedia, this.globals.opcio, this.pagenumber).subscribe(
            (res: ResponseWrapper) => {
                if (this.pagenumber === 0) {
                    this.filmography = res.json;
                } else {
                    console.log('json ');
                    console.log(res.json);
                    this.filmography = this.filmography.concat(res.json);
                    console.log('after concat', this.filmography);
                }
                console.log(this.filmography);
                this.loading = false;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );

    }

    loadToShow() {
        this.watchlistService.count(this.globals.multimedia, this.globals.opcio).subscribe(
            (res: number) => {
                this.toShow = res;
            } ,
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
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
