import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MovieStats } from './movie-stats.model';
import { MovieStatsPopupService } from './movie-stats-popup.service';
import { MovieStatsService } from './movie-stats.service';

@Component({
    selector: 'jhi-movie-stats-delete-dialog',
    templateUrl: './movie-stats-delete-dialog.component.html'
})
export class MovieStatsDeleteDialogComponent {

    movieStats: MovieStats;

    constructor(
        private movieStatsService: MovieStatsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.movieStatsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'movieStatsListModification',
                content: 'Deleted an movieStats'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-movie-stats-delete-popup',
    template: ''
})
export class MovieStatsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private movieStatsPopupService: MovieStatsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.movieStatsPopupService
                .open(MovieStatsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
