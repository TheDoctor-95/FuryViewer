import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ReviewSeries } from './review-series.model';
import { ReviewSeriesPopupService } from './review-series-popup.service';
import { ReviewSeriesService } from './review-series.service';

@Component({
    selector: 'jhi-review-series-delete-dialog',
    templateUrl: './review-series-delete-dialog.component.html'
})
export class ReviewSeriesDeleteDialogComponent {

    reviewSeries: ReviewSeries;

    constructor(
        private reviewSeriesService: ReviewSeriesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reviewSeriesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reviewSeriesListModification',
                content: 'Deleted an reviewSeries'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-review-series-delete-popup',
    template: ''
})
export class ReviewSeriesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reviewSeriesPopupService: ReviewSeriesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.reviewSeriesPopupService
                .open(ReviewSeriesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
