import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FavouriteArtist } from './favourite-artist.model';
import { FavouriteArtistPopupService } from './favourite-artist-popup.service';
import { FavouriteArtistService } from './favourite-artist.service';

@Component({
    selector: 'jhi-favourite-artist-delete-dialog',
    templateUrl: './favourite-artist-delete-dialog.component.html'
})
export class FavouriteArtistDeleteDialogComponent {

    favouriteArtist: FavouriteArtist;

    constructor(
        private favouriteArtistService: FavouriteArtistService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.favouriteArtistService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'favouriteArtistListModification',
                content: 'Deleted an favouriteArtist'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-favourite-artist-delete-popup',
    template: ''
})
export class FavouriteArtistDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private favouriteArtistPopupService: FavouriteArtistPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.favouriteArtistPopupService
                .open(FavouriteArtistDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
