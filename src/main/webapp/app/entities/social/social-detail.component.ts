import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Social } from './social.model';
import { SocialService } from './social.service';

@Component({
    selector: 'jhi-social-detail',
    templateUrl: './social-detail.component.html'
})
export class SocialDetailComponent implements OnInit, OnDestroy {

    social: Social;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private socialService: SocialService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSocials();
    }

    load(id) {
        this.socialService.find(id).subscribe((social) => {
            this.social = social;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSocials() {
        this.eventSubscriber = this.eventManager.subscribe(
            'socialListModification',
            (response) => this.load(this.social.id)
        );
    }
}
