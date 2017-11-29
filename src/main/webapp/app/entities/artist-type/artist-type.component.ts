import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ArtistType } from './artist-type.model';
import { ArtistTypeService } from './artist-type.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-artist-type',
    templateUrl: './artist-type.component.html'
})
export class ArtistTypeComponent implements OnInit, OnDestroy {
artistTypes: ArtistType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private artistTypeService: ArtistTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.artistTypeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.artistTypes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInArtistTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ArtistType) {
        return item.id;
    }
    registerChangeInArtistTypes() {
        this.eventSubscriber = this.eventManager.subscribe('artistTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
