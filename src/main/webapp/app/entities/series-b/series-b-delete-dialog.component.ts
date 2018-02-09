import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SeriesB } from './series-b.model';
import { SeriesBPopupService } from './series-b-popup.service';
import { SeriesBService } from './series-b.service';

@Component({
    selector: 'jhi-series-b-delete-dialog',
    templateUrl: './series-b-delete-dialog.component.html'
})
export class SeriesBDeleteDialogComponent {

    seriesB: SeriesB;

    constructor(
        private seriesBService: SeriesBService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.seriesBService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'seriesBListModification',
                content: 'Deleted an seriesB'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-series-b-delete-popup',
    template: ''
})
export class SeriesBDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seriesBPopupService: SeriesBPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.seriesBPopupService
                .open(SeriesBDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
