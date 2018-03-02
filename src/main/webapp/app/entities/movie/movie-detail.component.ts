import {Component, OnInit, OnDestroy, ViewChild, ElementRef, HostListener} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Movie } from './movie.model';
import { MovieService } from './movie.service';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';
import {factoryOrValue} from 'rxjs/operator/multicast';
import {FavouriteMovieService} from '../favourite-movie/favourite-movie.service';
import {Observable} from 'rxjs/Observable';
import {FavouriteMovie} from '../favourite-movie/favourite-movie.model';

@Component({
    selector: 'jhi-movie-detail',
    templateUrl: './movie-detail.component.html',
    providers: [NgbRatingConfig],
    styles: [`
    .star {
      font-size: 1.5rem;
      color: #b0c4de;
    }
    .filled {
      color: #1e90ff;
    }
    .bad {
      color: #deb0b0;
    }
    .filled.bad {
      color: #ff1e1e;
    }
  `]
})
export class MovieDetailComponent implements OnInit, OnDestroy {
    movie: Movie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    fav: FavouriteMovie;

    constructor(
        private eventManager: JhiEventManager,
        private movieService: MovieService,
        private route: ActivatedRoute,
        config: NgbRatingConfig,
        private favouriteMovieService: FavouriteMovieService
    ) {
        config.max = 5;
        this.fav = new FavouriteMovie();
        this.fav.liked=false;
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
            this.getIfLiked(params['id']);
        });
        this.registerChangeInMovies();

    }

    load(id) {
        this.movieService.find(id).subscribe((movie) => {
            this.movie = movie;
        });
    }
    previousState() {
        window.history.back();
    }

    like(liked: boolean) {
        this.subscribeToLikeResponse(
            this.favouriteMovieService.favorite(this.movie.id, liked)
        );
        this.fav.liked= !this.fav.liked

    }

    getIfLiked(id: number) {
        this.favouriteMovieService.favMovieUser(id).subscribe((favouriteMovie) => {
            console.log('load');
            this.fav = favouriteMovie;
        });
    }

    private subscribeToLikeResponse(result: Observable<FavouriteMovie>) {
        result.subscribe((res: Movie) =>
            this.onSaveSuccessLike(res), (res: Response) => this.onSaveErrorLike());
    }
    private onSaveSuccessLike(result: FavouriteMovie) {
        this.eventManager.broadcast({ name: 'favouriteMovieListModification', content: 'OK'});

    }

    private onSaveErrorLike() {

    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMovies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'movieListModification',
            (response) => this.load(this.movie.id)
        );
    }

}
