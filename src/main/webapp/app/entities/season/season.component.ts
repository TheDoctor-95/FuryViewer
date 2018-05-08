import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Season } from './season.model';
import { SeasonService } from './season.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import {EpisodeService} from '../episode/episode.service';
import {Episode} from '../episode/episode.model';

@Component({
    selector: 'jhi-season',
    templateUrl: './season.component.html',
    styleUrls: ['season.css']
})
export class SeasonComponent implements OnInit, OnDestroy {
seasons: Season[];
    currentAccount: any;
    eventSubscriber: Subscription;
    episodes: Episode[];

    constructor(
        private seasonService: SeasonService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private episodeService: EpisodeService
    ) {
    }

    loadAll() {
        this.seasonService.query().subscribe(
            (res: ResponseWrapper) => {
                this.seasons = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.loadCalendar();
        });
        this.registerChangeInSeasons();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Season) {
        return item.id;
    }
    registerChangeInSeasons() {
        this.eventSubscriber = this.eventManager.subscribe('seasonListModification', (response) => this.loadAll());
    }

    loadCalendar() {
        this.episodeService.calendar().subscribe(
            (res: ResponseWrapper) => {
                this.episodes = res.json;
                console.log(this.episodes);
            },
            (res: ResponseWrapper) => this.onError(res.json)
        )
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
