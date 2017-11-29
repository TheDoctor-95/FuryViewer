import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Episode } from './episode.model';
import { EpisodePopupService } from './episode-popup.service';
import { EpisodeService } from './episode.service';

@Component({
    selector: 'jhi-episode-delete-dialog',
    templateUrl: './episode-delete-dialog.component.html'
})
export class EpisodeDeleteDialogComponent {

    episode: Episode;

    constructor(
        private episodeService: EpisodeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.episodeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'episodeListModification',
                content: 'Deleted an episode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-episode-delete-popup',
    template: ''
})
export class EpisodeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private episodePopupService: EpisodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.episodePopupService
                .open(EpisodeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
