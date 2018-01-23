import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Episode } from './episode.model';
import { EpisodeService } from './episode.service';

@Component({
    selector: 'jhi-episode-detail',
    templateUrl: './episode-detail.component.html'
})
export class EpisodeDetailComponent implements OnInit, OnDestroy {

    episode: Episode;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private episodeService: EpisodeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEpisodes();
    }

    load(id) {
        this.episodeService.find(id).subscribe((episode) => {
            this.episode = episode;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEpisodes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'episodeListModification',
            (response) => this.load(this.episode.id)
        );
    }
}
