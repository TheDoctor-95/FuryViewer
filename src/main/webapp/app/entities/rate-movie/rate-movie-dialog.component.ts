import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RateMovie } from './rate-movie.model';
import { RateMoviePopupService } from './rate-movie-popup.service';
import { RateMovieService } from './rate-movie.service';
import { Movie, MovieService } from '../movie';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-rate-movie-dialog',
    templateUrl: './rate-movie-dialog.component.html'
})
export class RateMovieDialogComponent implements OnInit {

    rateMovie: RateMovie;
    isSaving: boolean;

    movies: Movie[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rateMovieService: RateMovieService,
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
        if (this.rateMovie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rateMovieService.update(this.rateMovie));
        } else {
            this.subscribeToSaveResponse(
                this.rateMovieService.create(this.rateMovie));
        }
    }

    private subscribeToSaveResponse(result: Observable<RateMovie>) {
        result.subscribe((res: RateMovie) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: RateMovie) {
        this.eventManager.broadcast({ name: 'rateMovieListModification', content: 'OK'});
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
    selector: 'jhi-rate-movie-popup',
    template: ''
})
export class RateMoviePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rateMoviePopupService: RateMoviePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.rateMoviePopupService
                    .open(RateMovieDialogComponent as Component, params['id']);
            } else {
                this.rateMoviePopupService
                    .open(RateMovieDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
