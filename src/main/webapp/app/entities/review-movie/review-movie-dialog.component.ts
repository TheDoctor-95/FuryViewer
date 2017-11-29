import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ReviewMovie } from './review-movie.model';
import { ReviewMoviePopupService } from './review-movie-popup.service';
import { ReviewMovieService } from './review-movie.service';
import { Movie, MovieService } from '../movie';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-review-movie-dialog',
    templateUrl: './review-movie-dialog.component.html'
})
export class ReviewMovieDialogComponent implements OnInit {

    reviewMovie: ReviewMovie;
    isSaving: boolean;

    movies: Movie[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private reviewMovieService: ReviewMovieService,
        private movieService: MovieService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.movieService.query()
            .subscribe((res: ResponseWrapper) => { this.movies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.reviewMovie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reviewMovieService.update(this.reviewMovie));
        } else {
            this.subscribeToSaveResponse(
                this.reviewMovieService.create(this.reviewMovie));
        }
    }

    private subscribeToSaveResponse(result: Observable<ReviewMovie>) {
        result.subscribe((res: ReviewMovie) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ReviewMovie) {
        this.eventManager.broadcast({ name: 'reviewMovieListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMovieById(index: number, item: Movie) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-review-movie-popup',
    template: ''
})
export class ReviewMoviePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reviewMoviePopupService: ReviewMoviePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.reviewMoviePopupService
                    .open(ReviewMovieDialogComponent as Component, params['id']);
            } else {
                this.reviewMoviePopupService
                    .open(ReviewMovieDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
