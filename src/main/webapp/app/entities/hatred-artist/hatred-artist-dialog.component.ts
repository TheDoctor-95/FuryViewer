import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HatredArtist } from './hatred-artist.model';
import { HatredArtistPopupService } from './hatred-artist-popup.service';
import { HatredArtistService } from './hatred-artist.service';
import { Artist, ArtistService } from '../artist';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-hatred-artist-dialog',
    templateUrl: './hatred-artist-dialog.component.html'
})
export class HatredArtistDialogComponent implements OnInit {

    hatredArtist: HatredArtist;
    isSaving: boolean;

    artists: Artist[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private hatredArtistService: HatredArtistService,
        private artistService: ArtistService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.artistService.query()
            .subscribe((res: ResponseWrapper) => { this.artists = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.hatredArtist.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hatredArtistService.update(this.hatredArtist));
        } else {
            this.subscribeToSaveResponse(
                this.hatredArtistService.create(this.hatredArtist));
        }
    }

    private subscribeToSaveResponse(result: Observable<HatredArtist>) {
        result.subscribe((res: HatredArtist) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: HatredArtist) {
        this.eventManager.broadcast({ name: 'hatredArtistListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-hatred-artist-popup',
    template: ''
})
export class HatredArtistPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hatredArtistPopupService: HatredArtistPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hatredArtistPopupService
                    .open(HatredArtistDialogComponent as Component, params['id']);
            } else {
                this.hatredArtistPopupService
                    .open(HatredArtistDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
