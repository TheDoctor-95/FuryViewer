import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ReviewSeries } from './review-series.model';
import { ReviewSeriesPopupService } from './review-series-popup.service';
import { ReviewSeriesService } from './review-series.service';
import { Series, SeriesService } from '../series';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-review-series-dialog',
    templateUrl: './review-series-dialog.component.html'
})
export class ReviewSeriesDialogComponent implements OnInit {

    reviewSeries: ReviewSeries;
    isSaving: boolean;

    series: Series[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private reviewSeriesService: ReviewSeriesService,
        private seriesService: SeriesService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.seriesService.query()
            .subscribe((res: ResponseWrapper) => { this.series = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.reviewSeries.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reviewSeriesService.update(this.reviewSeries));
        } else {
            this.subscribeToSaveResponse(
                this.reviewSeriesService.create(this.reviewSeries));
        }
    }

    private subscribeToSaveResponse(result: Observable<ReviewSeries>) {
        result.subscribe((res: ReviewSeries) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ReviewSeries) {
        this.eventManager.broadcast({ name: 'reviewSeriesListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-review-series-popup',
    template: ''
})
export class ReviewSeriesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reviewSeriesPopupService: ReviewSeriesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.reviewSeriesPopupService
                    .open(ReviewSeriesDialogComponent as Component, params['id']);
            } else {
                this.reviewSeriesPopupService
                    .open(ReviewSeriesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
