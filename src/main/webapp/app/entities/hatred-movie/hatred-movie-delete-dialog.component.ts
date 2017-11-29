import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HatredMovie } from './hatred-movie.model';
import { HatredMoviePopupService } from './hatred-movie-popup.service';
import { HatredMovieService } from './hatred-movie.service';

@Component({
    selector: 'jhi-hatred-movie-delete-dialog',
    templateUrl: './hatred-movie-delete-dialog.component.html'
})
export class HatredMovieDeleteDialogComponent {

    hatredMovie: HatredMovie;

    constructor(
        private hatredMovieService: HatredMovieService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hatredMovieService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hatredMovieListModification',
                content: 'Deleted an hatredMovie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hatred-movie-delete-popup',
    template: ''
})
export class HatredMovieDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hatredMoviePopupService: HatredMoviePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hatredMoviePopupService
                .open(HatredMovieDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
