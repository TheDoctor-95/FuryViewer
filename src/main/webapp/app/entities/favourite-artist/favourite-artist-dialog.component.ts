import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FavouriteArtist } from './favourite-artist.model';
import { FavouriteArtistPopupService } from './favourite-artist-popup.service';
import { FavouriteArtistService } from './favourite-artist.service';
import { Artist, ArtistService } from '../artist';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-favourite-artist-dialog',
    templateUrl: './favourite-artist-dialog.component.html'
})
export class FavouriteArtistDialogComponent implements OnInit {

    favouriteArtist: FavouriteArtist;
    isSaving: boolean;

    artists: Artist[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private favouriteArtistService: FavouriteArtistService,
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
        if (this.favouriteArtist.id !== undefined) {
            this.subscribeToSaveResponse(
                this.favouriteArtistService.update(this.favouriteArtist));
        } else {
            this.subscribeToSaveResponse(
                this.favouriteArtistService.create(this.favouriteArtist));
        }
    }

    private subscribeToSaveResponse(result: Observable<FavouriteArtist>) {
        result.subscribe((res: FavouriteArtist) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FavouriteArtist) {
        this.eventManager.broadcast({ name: 'favouriteArtistListModification', content: 'OK'});
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
    selector: 'jhi-favourite-artist-popup',
    template: ''
})
export class FavouriteArtistPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private favouriteArtistPopupService: FavouriteArtistPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.favouriteArtistPopupService
                    .open(FavouriteArtistDialogComponent as Component, params['id']);
            } else {
                this.favouriteArtistPopupService
                    .open(FavouriteArtistDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
