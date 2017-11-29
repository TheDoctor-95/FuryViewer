import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RateSeries } from './rate-series.model';
import { RateSeriesPopupService } from './rate-series-popup.service';
import { RateSeriesService } from './rate-series.service';
import { Series, SeriesService } from '../series';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-rate-series-dialog',
    templateUrl: './rate-series-dialog.component.html'
})
export class RateSeriesDialogComponent implements OnInit {

    rateSeries: RateSeries;
    isSaving: boolean;

    series: Series[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rateSeriesService: RateSeriesService,
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
        if (this.rateSeries.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rateSeriesService.update(this.rateSeries));
        } else {
            this.subscribeToSaveResponse(
                this.rateSeriesService.create(this.rateSeries));
        }
    }

    private subscribeToSaveResponse(result: Observable<RateSeries>) {
        result.subscribe((res: RateSeries) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: RateSeries) {
        this.eventManager.broadcast({ name: 'rateSeriesListModification', content: 'OK'});
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
    selector: 'jhi-rate-series-popup',
    template: ''
})
export class RateSeriesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rateSeriesPopupService: RateSeriesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.rateSeriesPopupService
                    .open(RateSeriesDialogComponent as Component, params['id']);
            } else {
                this.rateSeriesPopupService
                    .open(RateSeriesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
