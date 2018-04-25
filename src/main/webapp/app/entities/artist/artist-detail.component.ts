import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import { Artist } from './artist.model';
import { ArtistService } from './artist.service';
import {ResponseWrapper} from "../../shared";
import {FilmographyArtistModel} from "../../shared/model/filmographyArtist.model";

@Component({
    selector: 'jhi-artist-detail',
    templateUrl: './artist-detail.component.html',
    providers: [NgbRatingConfig],
    styles: [`
    .star {
      font-size: 1.5rem;
      color: #b0c4de;
    }
    .filled {
      color: #1e90ff;
    }
    .bad {
      color: #deb0b0;
    }
    .filled.bad {
      color: #ff1e1e;
    }
  `]
})
export class ArtistDetailComponent implements OnInit, OnDestroy {

    artist: Artist;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    public filmography: FilmographyArtistModel;

    constructor(
        private eventManager: JhiEventManager,
        private artistService: ArtistService,
        config: NgbRatingConfig,
        private route: ActivatedRoute,
        private jhiAlertService: JhiAlertService
    ) {
        config.max = 5;
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
            this.loadFilmography(params['id']);
            console.log(this.filmography)
        });
        this.registerChangeInArtists();
    }

    load(id) {
        this.artistService.find(id).subscribe((artist) => {
            this.artist = artist;
        });
    }

    loadFilmography(id: number){
        this.artistService.filmography(id).subscribe(
            (res: ResponseWrapper) => {
                this.filmography = res.json;
                console.log(this.filmography);
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
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
