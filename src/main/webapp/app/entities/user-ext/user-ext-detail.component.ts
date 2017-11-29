import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { UserExt } from './user-ext.model';
import { UserExtService } from './user-ext.service';

@Component({
    selector: 'jhi-user-ext-detail',
    templateUrl: './user-ext-detail.component.html'
})
export class UserExtDetailComponent implements OnInit, OnDestroy {

    userExt: UserExt;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private userExtService: UserExtService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserExts();
    }

    load(id) {
        this.userExtService.find(id).subscribe((userExt) => {
            this.userExt = userExt;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserExts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userExtListModification',
            (response) => this.load(this.userExt.id)
        );
    }
}
