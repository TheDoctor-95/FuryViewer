import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ReviewMovie } from './review-movie.model';
import { ReviewMovieService } from './review-movie.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-review-movie',
    templateUrl: './review-movie.component.html'
})
export class ReviewMovieComponent implements OnInit, OnDestroy {
reviewMovies: ReviewMovie[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private reviewMovieService: ReviewMovieService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.reviewMovieService.query().subscribe(
            (res: ResponseWrapper) => {
                this.reviewMovies = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInReviewMovies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ReviewMovie) {
        return item.id;
    }
    registerChangeInReviewMovies() {
        this.eventSubscriber = this.eventManager.subscribe('reviewMovieListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
