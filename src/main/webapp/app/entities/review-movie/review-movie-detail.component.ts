import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ReviewMovie } from './review-movie.model';
import { ReviewMovieService } from './review-movie.service';

@Component({
    selector: 'jhi-review-movie-detail',
    templateUrl: './review-movie-detail.component.html'
})
export class ReviewMovieDetailComponent implements OnInit, OnDestroy {

    reviewMovie: ReviewMovie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private reviewMovieService: ReviewMovieService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReviewMovies();
    }

    load(id) {
        this.reviewMovieService.find(id).subscribe((reviewMovie) => {
            this.reviewMovie = reviewMovie;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReviewMovies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'reviewMovieListModification',
            (response) => this.load(this.reviewMovie.id)
        );
    }
}
