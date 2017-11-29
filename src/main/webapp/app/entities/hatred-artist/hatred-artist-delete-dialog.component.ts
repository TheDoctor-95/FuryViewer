import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HatredArtist } from './hatred-artist.model';
import { HatredArtistPopupService } from './hatred-artist-popup.service';
import { HatredArtistService } from './hatred-artist.service';

@Component({
    selector: 'jhi-hatred-artist-delete-dialog',
    templateUrl: './hatred-artist-delete-dialog.component.html'
})
export class HatredArtistDeleteDialogComponent {

    hatredArtist: HatredArtist;

    constructor(
        private hatredArtistService: HatredArtistService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hatredArtistService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hatredArtistListModification',
                content: 'Deleted an hatredArtist'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hatred-artist-delete-popup',
    template: ''
})
export class HatredArtistDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hatredArtistPopupService: HatredArtistPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hatredArtistPopupService
                .open(HatredArtistDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
