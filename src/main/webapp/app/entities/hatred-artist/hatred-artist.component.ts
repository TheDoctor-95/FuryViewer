import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { HatredArtist } from './hatred-artist.model';
import { HatredArtistService } from './hatred-artist.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-hatred-artist',
    templateUrl: './hatred-artist.component.html'
})
export class HatredArtistComponent implements OnInit, OnDestroy {
hatredArtists: HatredArtist[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private hatredArtistService: HatredArtistService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.hatredArtistService.query().subscribe(
            (res: ResponseWrapper) => {
                this.hatredArtists = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHatredArtists();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HatredArtist) {
        return item.id;
    }
    registerChangeInHatredArtists() {
        this.eventSubscriber = this.eventManager.subscribe('hatredArtistListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
