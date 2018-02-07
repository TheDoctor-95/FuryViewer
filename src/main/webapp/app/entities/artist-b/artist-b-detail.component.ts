import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ArtistB } from './artist-b.model';
import { ArtistBService } from './artist-b.service';

@Component({
    selector: 'jhi-artist-b-detail',
    templateUrl: './artist-b-detail.component.html'
})
export class ArtistBDetailComponent implements OnInit, OnDestroy {

    artistB: ArtistB;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private artistBService: ArtistBService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArtistBS();
    }

    load(id) {
        this.artistBService.find(id).subscribe((artistB) => {
            this.artistB = artistB;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArtistBS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'artistBListModification',
            (response) => this.load(this.artistB.id)
        );
    }
}
