import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ArtistB } from './artist-b.model';
import { ArtistBPopupService } from './artist-b-popup.service';
import { ArtistBService } from './artist-b.service';
import { Country, CountryService } from '../country';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-artist-b-dialog',
    templateUrl: './artist-b-dialog.component.html'
})
export class ArtistBDialogComponent implements OnInit {

    artistB: ArtistB;
    isSaving: boolean;

    countries: Country[];
    birthdateDp: any;
    deathdateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private artistBService: ArtistBService,
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
        if (this.artistB.id !== undefined) {
            this.subscribeToSaveResponse(
                this.artistBService.update(this.artistB));
        } else {
            this.subscribeToSaveResponse(
                this.artistBService.create(this.artistB));
        }
    }

    private subscribeToSaveResponse(result: Observable<ArtistB>) {
        result.subscribe((res: ArtistB) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ArtistB) {
        this.eventManager.broadcast({ name: 'artistBListModification', content: 'OK'});
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
    selector: 'jhi-artist-b-popup',
    template: ''
})
export class ArtistBPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private artistBPopupService: ArtistBPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.artistBPopupService
                    .open(ArtistBDialogComponent as Component, params['id']);
            } else {
                this.artistBPopupService
                    .open(ArtistBDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
