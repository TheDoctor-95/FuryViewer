import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RateMovie } from './rate-movie.model';
import { RateMoviePopupService } from './rate-movie-popup.service';
import { RateMovieService } from './rate-movie.service';

@Component({
    selector: 'jhi-rate-movie-delete-dialog',
    templateUrl: './rate-movie-delete-dialog.component.html'
})
export class RateMovieDeleteDialogComponent {

    rateMovie: RateMovie;

    constructor(
        private rateMovieService: RateMovieService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rateMovieService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'rateMovieListModification',
                content: 'Deleted an rateMovie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rate-movie-delete-popup',
    template: ''
})
export class RateMovieDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rateMoviePopupService: RateMoviePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.rateMoviePopupService
                .open(RateMovieDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
