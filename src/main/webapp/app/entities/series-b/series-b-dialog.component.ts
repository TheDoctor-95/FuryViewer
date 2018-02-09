import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SeriesB } from './series-b.model';
import { SeriesBPopupService } from './series-b-popup.service';
import { SeriesBService } from './series-b.service';
import { Country, CountryService } from '../country';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-series-b-dialog',
    templateUrl: './series-b-dialog.component.html'
})
export class SeriesBDialogComponent implements OnInit {

    seriesB: SeriesB;
    isSaving: boolean;

    countries: Country[];
    release_dateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private seriesBService: SeriesBService,
        private countryService: CountryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.countryService.query()
            .subscribe((res: ResponseWrapper) => { this.countries = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.seriesB.id !== undefined) {
            this.subscribeToSaveResponse(
                this.seriesBService.update(this.seriesB));
        } else {
            this.subscribeToSaveResponse(
                this.seriesBService.create(this.seriesB));
        }
    }

    private subscribeToSaveResponse(result: Observable<SeriesB>) {
        result.subscribe((res: SeriesB) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SeriesB) {
        this.eventManager.broadcast({ name: 'seriesBListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCountryById(index: number, item: Country) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-series-b-popup',
    template: ''
})
export class SeriesBPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seriesBPopupService: SeriesBPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.seriesBPopupService
                    .open(SeriesBDialogComponent as Component, params['id']);
            } else {
                this.seriesBPopupService
                    .open(SeriesBDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
