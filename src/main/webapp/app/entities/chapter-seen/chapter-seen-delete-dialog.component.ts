import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ChapterSeen } from './chapter-seen.model';
import { ChapterSeenPopupService } from './chapter-seen-popup.service';
import { ChapterSeenService } from './chapter-seen.service';

@Component({
    selector: 'jhi-chapter-seen-delete-dialog',
    templateUrl: './chapter-seen-delete-dialog.component.html'
})
export class ChapterSeenDeleteDialogComponent {

    chapterSeen: ChapterSeen;

    constructor(
        private chapterSeenService: ChapterSeenService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.chapterSeenService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'chapterSeenListModification',
                content: 'Deleted an chapterSeen'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-chapter-seen-delete-popup',
    template: ''
})
export class ChapterSeenDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chapterSeenPopupService: ChapterSeenPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.chapterSeenPopupService
                .open(ChapterSeenDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
