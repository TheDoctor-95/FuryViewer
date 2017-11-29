import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FavouriteSeries } from './favourite-series.model';
import { FavouriteSeriesPopupService } from './favourite-series-popup.service';
import { FavouriteSeriesService } from './favourite-series.service';

@Component({
    selector: 'jhi-favourite-series-delete-dialog',
    templateUrl: './favourite-series-delete-dialog.component.html'
})
export class FavouriteSeriesDeleteDialogComponent {

    favouriteSeries: FavouriteSeries;

    constructor(
        private favouriteSeriesService: FavouriteSeriesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.favouriteSeriesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'favouriteSeriesListModification',
                content: 'Deleted an favouriteSeries'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-favourite-series-delete-popup',
    template: ''
})
export class FavouriteSeriesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private favouriteSeriesPopupService: FavouriteSeriesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.favouriteSeriesPopupService
                .open(FavouriteSeriesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
