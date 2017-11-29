import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RateMovie } from './rate-movie.model';
import { RateMovieService } from './rate-movie.service';

@Component({
    selector: 'jhi-rate-movie-detail',
    templateUrl: './rate-movie-detail.component.html'
})
export class RateMovieDetailComponent implements OnInit, OnDestroy {

    rateMovie: RateMovie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rateMovieService: RateMovieService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRateMovies();
    }

    load(id) {
        this.rateMovieService.find(id).subscribe((rateMovie) => {
            this.rateMovie = rateMovie;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRateMovies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'rateMovieListModification',
            (response) => this.load(this.rateMovie.id)
        );
    }
}
