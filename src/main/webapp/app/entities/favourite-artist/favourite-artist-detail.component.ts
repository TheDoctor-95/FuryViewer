import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FavouriteArtist } from './favourite-artist.model';
import { FavouriteArtistService } from './favourite-artist.service';

@Component({
    selector: 'jhi-favourite-artist-detail',
    templateUrl: './favourite-artist-detail.component.html'
})
export class FavouriteArtistDetailComponent implements OnInit, OnDestroy {

    favouriteArtist: FavouriteArtist;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private favouriteArtistService: FavouriteArtistService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFavouriteArtists();
    }

    load(id) {
        this.favouriteArtistService.find(id).subscribe((favouriteArtist) => {
            this.favouriteArtist = favouriteArtist;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFavouriteArtists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'favouriteArtistListModification',
            (response) => this.load(this.favouriteArtist.id)
        );
    }
}
