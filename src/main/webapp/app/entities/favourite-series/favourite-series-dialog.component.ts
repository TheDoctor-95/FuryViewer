import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FavouriteSeries } from './favourite-series.model';
import { FavouriteSeriesPopupService } from './favourite-series-popup.service';
import { FavouriteSeriesService } from './favourite-series.service';
import { Series, SeriesService } from '../series';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-favourite-series-dialog',
    templateUrl: './favourite-series-dialog.component.html'
})
export class FavouriteSeriesDialogComponent implements OnInit {

    favouriteSeries: FavouriteSeries;
    isSaving: boolean;

    series: Series[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private favouriteSeriesService: FavouriteSeriesService,
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
        if (this.favouriteSeries.id !== undefined) {
            this.subscribeToSaveResponse(
                this.favouriteSeriesService.update(this.favouriteSeries));
        } else {
            this.subscribeToSaveResponse(
                this.favouriteSeriesService.create(this.favouriteSeries));
        }
    }

    private subscribeToSaveResponse(result: Observable<FavouriteSeries>) {
        result.subscribe((res: FavouriteSeries) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FavouriteSeries) {
        this.eventManager.broadcast({ name: 'favouriteSeriesListModification', content: 'OK'});
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
    selector: 'jhi-favourite-series-popup',
    template: ''
})
export class FavouriteSeriesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private favouriteSeriesPopupService: FavouriteSeriesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.favouriteSeriesPopupService
                    .open(FavouriteSeriesDialogComponent as Component, params['id']);
            } else {
                this.favouriteSeriesPopupService
                    .open(FavouriteSeriesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
