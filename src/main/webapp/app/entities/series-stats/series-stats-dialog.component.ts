import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SeriesStats } from './series-stats.model';
import { SeriesStatsPopupService } from './series-stats-popup.service';
import { SeriesStatsService } from './series-stats.service';
import { Series, SeriesService } from '../series';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-series-stats-dialog',
    templateUrl: './series-stats-dialog.component.html'
})
export class SeriesStatsDialogComponent implements OnInit {

    seriesStats: SeriesStats;
    isSaving: boolean;

    series: Series[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private seriesStatsService: SeriesStatsService,
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
        if (this.seriesStats.id !== undefined) {
            this.subscribeToSaveResponse(
                this.seriesStatsService.update(this.seriesStats));
        } else {
            this.subscribeToSaveResponse(
                this.seriesStatsService.create(this.seriesStats));
        }
    }

    private subscribeToSaveResponse(result: Observable<SeriesStats>) {
        result.subscribe((res: SeriesStats) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SeriesStats) {
        this.eventManager.broadcast({ name: 'seriesStatsListModification', content: 'OK'});
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
    selector: 'jhi-series-stats-popup',
    template: ''
})
export class SeriesStatsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seriesStatsPopupService: SeriesStatsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.seriesStatsPopupService
                    .open(SeriesStatsDialogComponent as Component, params['id']);
            } else {
                this.seriesStatsPopupService
                    .open(SeriesStatsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
