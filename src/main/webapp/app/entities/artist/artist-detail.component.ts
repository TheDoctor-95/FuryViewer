import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Artist } from './artist.model';
import { ArtistService } from './artist.service';

@Component({
    selector: 'jhi-artist-detail',
    templateUrl: './artist-detail.component.html'
})
export class ArtistDetailComponent implements OnInit, OnDestroy {

    artist: Artist;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private artistService: ArtistService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArtists();
    }

    load(id) {
        this.artistService.find(id).subscribe((artist) => {
            this.artist = artist;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArtists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'artistListModification',
            (response) => this.load(this.artist.id)
        );
    }
}
