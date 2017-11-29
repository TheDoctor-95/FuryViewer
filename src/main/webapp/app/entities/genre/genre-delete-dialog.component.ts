import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Genre } from './genre.model';
import { GenrePopupService } from './genre-popup.service';
import { GenreService } from './genre.service';

@Component({
    selector: 'jhi-genre-delete-dialog',
    templateUrl: './genre-delete-dialog.component.html'
})
export class GenreDeleteDialogComponent {

    genre: Genre;

    constructor(
        private genreService: GenreService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.genreService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'genreListModification',
                content: 'Deleted an genre'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-genre-delete-popup',
    template: ''
})
export class GenreDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private genrePopupService: GenrePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.genrePopupService
                .open(GenreDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
