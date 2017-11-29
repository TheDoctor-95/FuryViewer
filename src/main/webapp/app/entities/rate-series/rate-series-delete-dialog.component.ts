import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RateSeries } from './rate-series.model';
import { RateSeriesPopupService } from './rate-series-popup.service';
import { RateSeriesService } from './rate-series.service';

@Component({
    selector: 'jhi-rate-series-delete-dialog',
    templateUrl: './rate-series-delete-dialog.component.html'
})
export class RateSeriesDeleteDialogComponent {

    rateSeries: RateSeries;

    constructor(
        private rateSeriesService: RateSeriesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rateSeriesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'rateSeriesListModification',
                content: 'Deleted an rateSeries'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rate-series-delete-popup',
    template: ''
})
export class RateSeriesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rateSeriesPopupService: RateSeriesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.rateSeriesPopupService
                .open(RateSeriesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
