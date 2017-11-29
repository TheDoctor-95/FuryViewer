import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ArtistType } from './artist-type.model';
import { ArtistTypePopupService } from './artist-type-popup.service';
import { ArtistTypeService } from './artist-type.service';
import { Artist, ArtistService } from '../artist';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-artist-type-dialog',
    templateUrl: './artist-type-dialog.component.html'
})
export class ArtistTypeDialogComponent implements OnInit {

    artistType: ArtistType;
    isSaving: boolean;

    artists: Artist[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private artistTypeService: ArtistTypeService,
        private artistService: ArtistService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.artistService.query()
            .subscribe((res: ResponseWrapper) => { this.artists = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.artistType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.artistTypeService.update(this.artistType));
        } else {
            this.subscribeToSaveResponse(
                this.artistTypeService.create(this.artistType));
        }
    }

    private subscribeToSaveResponse(result: Observable<ArtistType>) {
        result.subscribe((res: ArtistType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ArtistType) {
        this.eventManager.broadcast({ name: 'artistTypeListModification', content: 'OK'});
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
    selector: 'jhi-artist-type-popup',
    template: ''
})
export class ArtistTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private artistTypePopupService: ArtistTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.artistTypePopupService
                    .open(ArtistTypeDialogComponent as Component, params['id']);
            } else {
                this.artistTypePopupService
                    .open(ArtistTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
