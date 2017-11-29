import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Genre } from './genre.model';
import { GenreService } from './genre.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-genre',
    templateUrl: './genre.component.html'
})
export class GenreComponent implements OnInit, OnDestroy {
genres: Genre[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private genreService: GenreService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.genreService.query().subscribe(
            (res: ResponseWrapper) => {
                this.genres = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGenres();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Genre) {
        return item.id;
    }
    registerChangeInGenres() {
        this.eventSubscriber = this.eventManager.subscribe('genreListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
