import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ChapterSeen } from './chapter-seen.model';
import { ChapterSeenService } from './chapter-seen.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-chapter-seen',
    templateUrl: './chapter-seen.component.html'
})
export class ChapterSeenComponent implements OnInit, OnDestroy {
chapterSeens: ChapterSeen[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private chapterSeenService: ChapterSeenService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.chapterSeenService.query().subscribe(
            (res: ResponseWrapper) => {
                this.chapterSeens = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInChapterSeens();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ChapterSeen) {
        return item.id;
    }
    registerChangeInChapterSeens() {
        this.eventSubscriber = this.eventManager.subscribe('chapterSeenListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
