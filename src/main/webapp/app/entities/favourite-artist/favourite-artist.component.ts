import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { FavouriteArtist } from './favourite-artist.model';
import { FavouriteArtistService } from './favourite-artist.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-favourite-artist',
    templateUrl: './favourite-artist.component.html'
})
export class FavouriteArtistComponent implements OnInit, OnDestroy {
favouriteArtists: FavouriteArtist[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private favouriteArtistService: FavouriteArtistService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.favouriteArtistService.query().subscribe(
            (res: ResponseWrapper) => {
                this.favouriteArtists = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFavouriteArtists();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FavouriteArtist) {
        return item.id;
    }
    registerChangeInFavouriteArtists() {
        this.eventSubscriber = this.eventManager.subscribe('favouriteArtistListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
