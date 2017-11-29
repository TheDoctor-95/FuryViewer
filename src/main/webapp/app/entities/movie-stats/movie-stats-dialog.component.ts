import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MovieStats } from './movie-stats.model';
import { MovieStatsPopupService } from './movie-stats-popup.service';
import { MovieStatsService } from './movie-stats.service';
import { Movie, MovieService } from '../movie';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-movie-stats-dialog',
    templateUrl: './movie-stats-dialog.component.html'
})
export class MovieStatsDialogComponent implements OnInit {

    movieStats: MovieStats;
    isSaving: boolean;

    movies: Movie[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private movieStatsService: MovieStatsService,
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
        if (this.movieStats.id !== undefined) {
            this.subscribeToSaveResponse(
                this.movieStatsService.update(this.movieStats));
        } else {
            this.subscribeToSaveResponse(
                this.movieStatsService.create(this.movieStats));
        }
    }

    private subscribeToSaveResponse(result: Observable<MovieStats>) {
        result.subscribe((res: MovieStats) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MovieStats) {
        this.eventManager.broadcast({ name: 'movieStatsListModification', content: 'OK'});
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
    selector: 'jhi-movie-stats-popup',
    template: ''
})
export class MovieStatsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private movieStatsPopupService: MovieStatsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.movieStatsPopupService
                    .open(MovieStatsDialogComponent as Component, params['id']);
            } else {
                this.movieStatsPopupService
                    .open(MovieStatsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
