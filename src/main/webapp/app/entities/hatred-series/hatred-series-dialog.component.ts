import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HatredSeries } from './hatred-series.model';
import { HatredSeriesPopupService } from './hatred-series-popup.service';
import { HatredSeriesService } from './hatred-series.service';
import { Series, SeriesService } from '../series';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-hatred-series-dialog',
    templateUrl: './hatred-series-dialog.component.html'
})
export class HatredSeriesDialogComponent implements OnInit {

    hatredSeries: HatredSeries;
    isSaving: boolean;

    series: Series[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private hatredSeriesService: HatredSeriesService,
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
        if (this.hatredSeries.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hatredSeriesService.update(this.hatredSeries));
        } else {
            this.subscribeToSaveResponse(
                this.hatredSeriesService.create(this.hatredSeries));
        }
    }

    private subscribeToSaveResponse(result: Observable<HatredSeries>) {
        result.subscribe((res: HatredSeries) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: HatredSeries) {
        this.eventManager.broadcast({ name: 'hatredSeriesListModification', content: 'OK'});
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
    selector: 'jhi-hatred-series-popup',
    template: ''
})
export class HatredSeriesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hatredSeriesPopupService: HatredSeriesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hatredSeriesPopupService
                    .open(HatredSeriesDialogComponent as Component, params['id']);
            } else {
                this.hatredSeriesPopupService
                    .open(HatredSeriesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
