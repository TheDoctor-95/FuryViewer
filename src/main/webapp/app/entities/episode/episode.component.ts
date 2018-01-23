import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Episode } from './episode.model';
import { EpisodeService } from './episode.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-episode',
    templateUrl: './episode.component.html'
})
export class EpisodeComponent implements OnInit, OnDestroy {
episodes: Episode[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private episodeService: EpisodeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.episodeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.episodes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEpisodes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Episode) {
        return item.id;
    }
    registerChangeInEpisodes() {
        this.eventSubscriber = this.eventManager.subscribe('episodeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
