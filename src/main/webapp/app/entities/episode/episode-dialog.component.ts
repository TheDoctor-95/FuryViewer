import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Episode } from './episode.model';
import { EpisodePopupService } from './episode-popup.service';
import { EpisodeService } from './episode.service';
import { Season, SeasonService } from '../season';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-episode-dialog',
    templateUrl: './episode-dialog.component.html'
})
export class EpisodeDialogComponent implements OnInit {

    episode: Episode;
    isSaving: boolean;

    seasons: Season[];
    releaseDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private episodeService: EpisodeService,
        private seasonService: SeasonService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.seasonService.query()
            .subscribe((res: ResponseWrapper) => { this.seasons = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.episode, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.episode.id !== undefined) {
            this.subscribeToSaveResponse(
                this.episodeService.update(this.episode));
        } else {
            this.subscribeToSaveResponse(
                this.episodeService.create(this.episode));
        }
    }

    private subscribeToSaveResponse(result: Observable<Episode>) {
        result.subscribe((res: Episode) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Episode) {
        this.eventManager.broadcast({ name: 'episodeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSeasonById(index: number, item: Season) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-episode-popup',
    template: ''
})
export class EpisodePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private episodePopupService: EpisodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.episodePopupService
                    .open(EpisodeDialogComponent as Component, params['id']);
            } else {
                this.episodePopupService
                    .open(EpisodeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
