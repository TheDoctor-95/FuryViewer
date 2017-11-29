import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Social } from './social.model';
import { SocialService } from './social.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-social',
    templateUrl: './social.component.html'
})
export class SocialComponent implements OnInit, OnDestroy {
socials: Social[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private socialService: SocialService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.socialService.query().subscribe(
            (res: ResponseWrapper) => {
                this.socials = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSocials();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Social) {
        return item.id;
    }
    registerChangeInSocials() {
        this.eventSubscriber = this.eventManager.subscribe('socialListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
