import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Movie } from './movie.model';
import { MoviePopupService } from './movie-popup.service';
import { MovieService } from './movie.service';

@Component({
    selector: 'jhi-movie-delete-dialog',
    templateUrl: './movie-delete-dialog.component.html'
})
export class MovieDeleteDialogComponent {

    movie: Movie;

    constructor(
        private movieService: MovieService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.movieService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'movieListModification',
                content: 'Deleted an movie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-movie-delete-popup',
    template: ''
})
export class MovieDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private moviePopupService: MoviePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.moviePopupService
                .open(MovieDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
