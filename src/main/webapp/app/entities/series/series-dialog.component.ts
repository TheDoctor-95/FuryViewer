import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Series } from './series.model';
import { SeriesPopupService } from './series-popup.service';
import { SeriesService } from './series.service';
import { Artist, ArtistService } from '../artist';
import { Company, CompanyService } from '../company';
import { Genre, GenreService } from '../genre';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-series-dialog',
    templateUrl: './series-dialog.component.html'
})
export class SeriesDialogComponent implements OnInit {

    series: Series;
    isSaving: boolean;

    artists: Artist[];

    companies: Company[];

    genres: Genre[];
    releaseDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private seriesService: SeriesService,
        private artistService: ArtistService,
        private companyService: CompanyService,
        private genreService: GenreService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.artistService.query()
            .subscribe((res: ResponseWrapper) => { this.artists = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.genreService.query()
            .subscribe((res: ResponseWrapper) => { this.genres = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.series.id !== undefined) {
            this.subscribeToSaveResponse(
                this.seriesService.update(this.series));
        } else {
            this.subscribeToSaveResponse(
                this.seriesService.create(this.series));
        }
    }

    private subscribeToSaveResponse(result: Observable<Series>) {
        result.subscribe((res: Series) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Series) {
        this.eventManager.broadcast({ name: 'seriesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackArtistById(index: number, item: Artist) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackGenreById(index: number, item: Genre) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-series-popup',
    template: ''
})
export class SeriesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seriesPopupService: SeriesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.seriesPopupService
                    .open(SeriesDialogComponent as Component, params['id']);
            } else {
                this.seriesPopupService
                    .open(SeriesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
