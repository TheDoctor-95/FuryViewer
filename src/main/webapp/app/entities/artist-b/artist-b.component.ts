import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ArtistB } from './artist-b.model';
import { ArtistBService } from './artist-b.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-artist-b',
    templateUrl: './artist-b.component.html'
})
export class ArtistBComponent implements OnInit, OnDestroy {
artistBS: ArtistB[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private artistBService: ArtistBService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.artistBService.query().subscribe(
            (res: ResponseWrapper) => {
                this.artistBS = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInArtistBS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ArtistB) {
        return item.id;
    }
    registerChangeInArtistBS() {
        this.eventSubscriber = this.eventManager.subscribe('artistBListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
