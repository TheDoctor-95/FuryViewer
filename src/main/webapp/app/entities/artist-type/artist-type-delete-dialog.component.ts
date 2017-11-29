import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ArtistType } from './artist-type.model';
import { ArtistTypePopupService } from './artist-type-popup.service';
import { ArtistTypeService } from './artist-type.service';

@Component({
    selector: 'jhi-artist-type-delete-dialog',
    templateUrl: './artist-type-delete-dialog.component.html'
})
export class ArtistTypeDeleteDialogComponent {

    artistType: ArtistType;

    constructor(
        private artistTypeService: ArtistTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.artistTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'artistTypeListModification',
                content: 'Deleted an artistType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-artist-type-delete-popup',
    template: ''
})
export class ArtistTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private artistTypePopupService: ArtistTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.artistTypePopupService
                .open(ArtistTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
