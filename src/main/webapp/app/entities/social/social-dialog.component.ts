import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Social } from './social.model';
import { SocialPopupService } from './social-popup.service';
import { SocialService } from './social.service';
import { Series, SeriesService } from '../series';
import { Movie, MovieService } from '../movie';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-social-dialog',
    templateUrl: './social-dialog.component.html'
})
export class SocialDialogComponent implements OnInit {

    social: Social;
    isSaving: boolean;

    series: Series[];

    movies: Movie[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private socialService: SocialService,
        private seriesService: SeriesService,
        private movieService: MovieService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.seriesService.query()
            .subscribe((res: ResponseWrapper) => { this.series = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.movieService.query()
            .subscribe((res: ResponseWrapper) => { this.movies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.social.id !== undefined) {
            this.subscribeToSaveResponse(
                this.socialService.update(this.social));
        } else {
            this.subscribeToSaveResponse(
                this.socialService.create(this.social));
        }
    }

    private subscribeToSaveResponse(result: Observable<Social>) {
        result.subscribe((res: Social) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Social) {
        this.eventManager.broadcast({ name: 'socialListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSeriesById(index: number, item: Series) {
        return item.id;
    }

    trackMovieById(index: number, item: Movie) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-social-popup',
    template: ''
})
export class SocialPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private socialPopupService: SocialPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.socialPopupService
                    .open(SocialDialogComponent as Component, params['id']);
            } else {
                this.socialPopupService
                    .open(SocialDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
