import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HatredMovie } from './hatred-movie.model';
import { HatredMoviePopupService } from './hatred-movie-popup.service';
import { HatredMovieService } from './hatred-movie.service';
import { Movie, MovieService } from '../movie';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-hatred-movie-dialog',
    templateUrl: './hatred-movie-dialog.component.html'
})
export class HatredMovieDialogComponent implements OnInit {

    hatredMovie: HatredMovie;
    isSaving: boolean;

    movies: Movie[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private hatredMovieService: HatredMovieService,
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
        if (this.hatredMovie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hatredMovieService.update(this.hatredMovie));
        } else {
            this.subscribeToSaveResponse(
                this.hatredMovieService.create(this.hatredMovie));
        }
    }

    private subscribeToSaveResponse(result: Observable<HatredMovie>) {
        result.subscribe((res: HatredMovie) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: HatredMovie) {
        this.eventManager.broadcast({ name: 'hatredMovieListModification', content: 'OK'});
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
    selector: 'jhi-hatred-movie-popup',
    template: ''
})
export class HatredMoviePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hatredMoviePopupService: HatredMoviePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hatredMoviePopupService
                    .open(HatredMovieDialogComponent as Component, params['id']);
            } else {
                this.hatredMoviePopupService
                    .open(HatredMovieDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
