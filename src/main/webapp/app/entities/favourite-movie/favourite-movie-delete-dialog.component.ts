import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FavouriteMovie } from './favourite-movie.model';
import { FavouriteMoviePopupService } from './favourite-movie-popup.service';
import { FavouriteMovieService } from './favourite-movie.service';

@Component({
    selector: 'jhi-favourite-movie-delete-dialog',
    templateUrl: './favourite-movie-delete-dialog.component.html'
})
export class FavouriteMovieDeleteDialogComponent {

    favouriteMovie: FavouriteMovie;

    constructor(
        private favouriteMovieService: FavouriteMovieService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.favouriteMovieService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'favouriteMovieListModification',
                content: 'Deleted an favouriteMovie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-favourite-movie-delete-popup',
    template: ''
})
export class FavouriteMovieDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private favouriteMoviePopupService: FavouriteMoviePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.favouriteMoviePopupService
                .open(FavouriteMovieDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
