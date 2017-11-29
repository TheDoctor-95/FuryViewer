import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { HatredArtist } from './hatred-artist.model';
import { HatredArtistService } from './hatred-artist.service';

@Component({
    selector: 'jhi-hatred-artist-detail',
    templateUrl: './hatred-artist-detail.component.html'
})
export class HatredArtistDetailComponent implements OnInit, OnDestroy {

    hatredArtist: HatredArtist;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hatredArtistService: HatredArtistService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHatredArtists();
    }

    load(id) {
        this.hatredArtistService.find(id).subscribe((hatredArtist) => {
            this.hatredArtist = hatredArtist;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHatredArtists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hatredArtistListModification',
            (response) => this.load(this.hatredArtist.id)
        );
    }
}
