import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FavouriteMovie } from './favourite-movie.model';
import { FavouriteMovieService } from './favourite-movie.service';

@Component({
    selector: 'jhi-favourite-movie-detail',
    templateUrl: './favourite-movie-detail.component.html'
})
export class FavouriteMovieDetailComponent implements OnInit, OnDestroy {

    favouriteMovie: FavouriteMovie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private favouriteMovieService: FavouriteMovieService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFavouriteMovies();
    }

    load(id) {
        this.favouriteMovieService.find(id).subscribe((favouriteMovie) => {
            this.favouriteMovie = favouriteMovie;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFavouriteMovies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'favouriteMovieListModification',
            (response) => this.load(this.favouriteMovie.id)
        );
    }
}
