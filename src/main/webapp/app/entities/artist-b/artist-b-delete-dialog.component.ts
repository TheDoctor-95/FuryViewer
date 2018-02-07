import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ArtistB } from './artist-b.model';
import { ArtistBPopupService } from './artist-b-popup.service';
import { ArtistBService } from './artist-b.service';

@Component({
    selector: 'jhi-artist-b-delete-dialog',
    templateUrl: './artist-b-delete-dialog.component.html'
})
export class ArtistBDeleteDialogComponent {

    artistB: ArtistB;

    constructor(
        private artistBService: ArtistBService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.artistBService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'artistBListModification',
                content: 'Deleted an artistB'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-artist-b-delete-popup',
    template: ''
})
export class ArtistBDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private artistBPopupService: ArtistBPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.artistBPopupService
                .open(ArtistBDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
