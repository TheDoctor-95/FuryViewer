import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ChapterSeen } from './chapter-seen.model';
import { ChapterSeenService } from './chapter-seen.service';

@Component({
    selector: 'jhi-chapter-seen-detail',
    templateUrl: './chapter-seen-detail.component.html'
})
export class ChapterSeenDetailComponent implements OnInit, OnDestroy {

    chapterSeen: ChapterSeen;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private chapterSeenService: ChapterSeenService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInChapterSeens();
    }

    load(id) {
        this.chapterSeenService.find(id).subscribe((chapterSeen) => {
            this.chapterSeen = chapterSeen;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInChapterSeens() {
        this.eventSubscriber = this.eventManager.subscribe(
            'chapterSeenListModification',
            (response) => this.load(this.chapterSeen.id)
        );
    }
}
