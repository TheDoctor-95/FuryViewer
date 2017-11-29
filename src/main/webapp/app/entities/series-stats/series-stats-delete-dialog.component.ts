import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SeriesStats } from './series-stats.model';
import { SeriesStatsPopupService } from './series-stats-popup.service';
import { SeriesStatsService } from './series-stats.service';

@Component({
    selector: 'jhi-series-stats-delete-dialog',
    templateUrl: './series-stats-delete-dialog.component.html'
})
export class SeriesStatsDeleteDialogComponent {

    seriesStats: SeriesStats;

    constructor(
        private seriesStatsService: SeriesStatsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.seriesStatsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'seriesStatsListModification',
                content: 'Deleted an seriesStats'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-series-stats-delete-popup',
    template: ''
})
export class SeriesStatsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seriesStatsPopupService: SeriesStatsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.seriesStatsPopupService
                .open(SeriesStatsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
