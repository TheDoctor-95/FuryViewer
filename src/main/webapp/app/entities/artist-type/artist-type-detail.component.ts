import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ArtistType } from './artist-type.model';
import { ArtistTypeService } from './artist-type.service';

@Component({
    selector: 'jhi-artist-type-detail',
    templateUrl: './artist-type-detail.component.html'
})
export class ArtistTypeDetailComponent implements OnInit, OnDestroy {

    artistType: ArtistType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private artistTypeService: ArtistTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArtistTypes();
    }

    load(id) {
        this.artistTypeService.find(id).subscribe((artistType) => {
            this.artistType = artistType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArtistTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'artistTypeListModification',
            (response) => this.load(this.artistType.id)
        );
    }
}
