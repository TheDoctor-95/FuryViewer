import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HatredMovie } from './hatred-movie.model';
import { HatredMovieService } from './hatred-movie.service';

@Component({
    selector: 'jhi-hatred-movie-detail',
    templateUrl: './hatred-movie-detail.component.html'
})
export class HatredMovieDetailComponent implements OnInit, OnDestroy {

    hatredMovie: HatredMovie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hatredMovieService: HatredMovieService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHatredMovies();
    }

    load(id) {
        this.hatredMovieService.find(id).subscribe((hatredMovie) => {
            this.hatredMovie = hatredMovie;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHatredMovies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hatredMovieListModification',
            (response) => this.load(this.hatredMovie.id)
        );
    }
}
