import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ChapterSeen } from './chapter-seen.model';
import { ChapterSeenPopupService } from './chapter-seen-popup.service';
import { ChapterSeenService } from './chapter-seen.service';
import { Episode, EpisodeService } from '../episode';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-chapter-seen-dialog',
    templateUrl: './chapter-seen-dialog.component.html'
})
export class ChapterSeenDialogComponent implements OnInit {

    chapterSeen: ChapterSeen;
    isSaving: boolean;

    episodes: Episode[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private chapterSeenService: ChapterSeenService,
        private episodeService: EpisodeService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.episodeService.query()
            .subscribe((res: ResponseWrapper) => { this.episodes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.chapterSeen.id !== undefined) {
            this.subscribeToSaveResponse(
                this.chapterSeenService.update(this.chapterSeen));
        } else {
            this.subscribeToSaveResponse(
                this.chapterSeenService.create(this.chapterSeen));
        }
    }

    private subscribeToSaveResponse(result: Observable<ChapterSeen>) {
        result.subscribe((res: ChapterSeen) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ChapterSeen) {
        this.eventManager.broadcast({ name: 'chapterSeenListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEpisodeById(index: number, item: Episode) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-chapter-seen-popup',
    template: ''
})
export class ChapterSeenPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chapterSeenPopupService: ChapterSeenPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.chapterSeenPopupService
                    .open(ChapterSeenDialogComponent as Component, params['id']);
            } else {
                this.chapterSeenPopupService
                    .open(ChapterSeenDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
