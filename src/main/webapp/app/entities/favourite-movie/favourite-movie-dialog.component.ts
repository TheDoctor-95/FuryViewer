import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FavouriteMovie } from './favourite-movie.model';
import { FavouriteMoviePopupService } from './favourite-movie-popup.service';
import { FavouriteMovieService } from './favourite-movie.service';
import { Movie, MovieService } from '../movie';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-favourite-movie-dialog',
    templateUrl: './favourite-movie-dialog.component.html'
})
export class FavouriteMovieDialogComponent implements OnInit {

    favouriteMovie: FavouriteMovie;
    isSaving: boolean;

    movies: Movie[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private favouriteMovieService: FavouriteMovieService,
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
        if (this.favouriteMovie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.favouriteMovieService.update(this.favouriteMovie));
        } else {
            this.subscribeToSaveResponse(
                this.favouriteMovieService.create(this.favouriteMovie));
        }
    }

    private subscribeToSaveResponse(result: Observable<FavouriteMovie>) {
        result.subscribe((res: FavouriteMovie) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FavouriteMovie) {
        this.eventManager.broadcast({ name: 'favouriteMovieListModification', content: 'OK'});
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
    selector: 'jhi-favourite-movie-popup',
    template: ''
})
export class FavouriteMoviePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private favouriteMoviePopupService: FavouriteMoviePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.favouriteMoviePopupService
                    .open(FavouriteMovieDialogComponent as Component, params['id']);
            } else {
                this.favouriteMoviePopupService
                    .open(FavouriteMovieDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
