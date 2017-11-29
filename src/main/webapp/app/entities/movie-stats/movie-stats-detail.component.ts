import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { MovieStats } from './movie-stats.model';
import { MovieStatsService } from './movie-stats.service';

@Component({
    selector: 'jhi-movie-stats-detail',
    templateUrl: './movie-stats-detail.component.html'
})
export class MovieStatsDetailComponent implements OnInit, OnDestroy {

    movieStats: MovieStats;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private movieStatsService: MovieStatsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMovieStats();
    }

    load(id) {
        this.movieStatsService.find(id).subscribe((movieStats) => {
            this.movieStats = movieStats;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMovieStats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'movieStatsListModification',
            (response) => this.load(this.movieStats.id)
        );
    }
}
