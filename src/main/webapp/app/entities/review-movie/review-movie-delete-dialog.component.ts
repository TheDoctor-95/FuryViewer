import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ReviewMovie } from './review-movie.model';
import { ReviewMoviePopupService } from './review-movie-popup.service';
import { ReviewMovieService } from './review-movie.service';

@Component({
    selector: 'jhi-review-movie-delete-dialog',
    templateUrl: './review-movie-delete-dialog.component.html'
})
export class ReviewMovieDeleteDialogComponent {

    reviewMovie: ReviewMovie;

    constructor(
        private reviewMovieService: ReviewMovieService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reviewMovieService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reviewMovieListModification',
                content: 'Deleted an reviewMovie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-review-movie-delete-popup',
    template: ''
})
export class ReviewMovieDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reviewMoviePopupService: ReviewMoviePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.reviewMoviePopupService
                .open(ReviewMovieDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
