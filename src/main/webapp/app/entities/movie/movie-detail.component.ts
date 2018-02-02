import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Movie } from './movie.model';
import { MovieService } from './movie.service';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';

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

    constructor(
        private eventManager: JhiEventManager,
        private movieService: MovieService,
        private route: ActivatedRoute,
        config: NgbRatingConfig
    ) {
        config.max = 5;
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
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
