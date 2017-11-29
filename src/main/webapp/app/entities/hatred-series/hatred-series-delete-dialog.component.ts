import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HatredSeries } from './hatred-series.model';
import { HatredSeriesPopupService } from './hatred-series-popup.service';
import { HatredSeriesService } from './hatred-series.service';

@Component({
    selector: 'jhi-hatred-series-delete-dialog',
    templateUrl: './hatred-series-delete-dialog.component.html'
})
export class HatredSeriesDeleteDialogComponent {

    hatredSeries: HatredSeries;

    constructor(
        private hatredSeriesService: HatredSeriesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hatredSeriesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hatredSeriesListModification',
                content: 'Deleted an hatredSeries'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hatred-series-delete-popup',
    template: ''
})
export class HatredSeriesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hatredSeriesPopupService: HatredSeriesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hatredSeriesPopupService
                .open(HatredSeriesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
