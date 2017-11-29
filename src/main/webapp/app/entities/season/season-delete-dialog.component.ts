import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Season } from './season.model';
import { SeasonPopupService } from './season-popup.service';
import { SeasonService } from './season.service';

@Component({
    selector: 'jhi-season-delete-dialog',
    templateUrl: './season-delete-dialog.component.html'
})
export class SeasonDeleteDialogComponent {

    season: Season;

    constructor(
        private seasonService: SeasonService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.seasonService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'seasonListModification',
                content: 'Deleted an season'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-season-delete-popup',
    template: ''
})
export class SeasonDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seasonPopupService: SeasonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.seasonPopupService
                .open(SeasonDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
