import { Component, OnInit, OnDestroy } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import { Artist } from './artist.model';
import { ArtistService } from './artist.service';
import {ResponseWrapper} from '../../shared';
import {FilmographyArtistModel} from '../../shared/model/filmographyArtist.model';
import {type} from 'os';
import {Observable} from 'rxjs/Observable';
import {HatredArtist} from '../hatred-artist';
import {HatredArtistService} from '../hatred-artist';
import {FavouriteArtist, FavouriteArtistService} from '../favourite-artist';
import {BooleanModel} from "../../shared/model/boolean.model";

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
    fav: boolean;
    hate: boolean;
    progres: number;
    votesCount: string;

    constructor(
        private eventManager: JhiEventManager,
        private artistService: ArtistService,
        config: NgbRatingConfig,
        private route: ActivatedRoute,
        private jhiAlertService: JhiAlertService,
        private hatredArtistService: HatredArtistService,
        private favouriteArtistService: FavouriteArtistService,
        public router: Router
    ) {
        config.max = 5;
        this.progres = 0;
        this.votesCount = '0';
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
            this.loadFilmography(params['id']);
            this.loadFavHate(params['id']);
            this.loadCountFavHate(params['id']);
            this.loadHate(params['id']);
            this.loadFav(params['id']);
        });
        this.registerChangeInArtists();
    }

    loadHate(id: number) {
        this.hatredArtistService.getHate(id).subscribe(
            (res) => {
                this.hate = res.like;
            }
        )
    }
    loadFav(id: number) {
        this.favouriteArtistService.fav(id).subscribe(
            (res) => {
                this.fav = res.like;
            }
        )
    }

    goTo(type: String, id: number) {
        switch (type) {
            case 'series' : {
                this.router.navigate(['/series', id]);
                break;
            }
            case 'movie' : {
                this.router.navigate(['/movie', id]);
                break;
            }
        }
    }

    load(id) {
        this.artistService.find(id).subscribe((artist) => {
            this.artist = artist;
        });
    }

    loadFilmography(id: number) {
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

    hated() {
        this.subscribeToSaveResponseHate(
            this.hatredArtistService.hatred(this.artist.id)
        );
    }

    private subscribeToSaveResponseHate(result: Observable<HatredArtist>) {
        result.subscribe((res: HatredArtist) =>
            this.onSaveSuccessHatred(res), (res: Response) => this.onSaveErrorHatred());
    }

    private onSaveSuccessHatred(result: HatredArtist) {
        this.eventManager.broadcast({ name: 'hatredArtistListModification', content: 'OK'});
        this.hate = result.hated;
        if (this.hate && this.fav) {
            this.subscribeToSaveResponseLiked(
                this.favouriteArtistService.favourite(this.artist.id)
            );
        }
        this.loadFavHate(this.artist.id);
        this.loadCountFavHate(this.artist.id);
    }

    private onSaveErrorHatred() {
    }

    liked() {
        this.subscribeToSaveResponseLiked(
            this.favouriteArtistService.favourite(this.artist.id)
        );
    }

    private subscribeToSaveResponseLiked(result: Observable<FavouriteArtist>) {
        result.subscribe((res: FavouriteArtist) =>
            this.onSaveSuccessLiked(res), (res: Response) => this.onSaveErrorLiked());
    }

    private onSaveSuccessLiked(result: FavouriteArtist) {
        this.eventManager.broadcast({ name: 'favouriteArtistListModification', content: 'OK'});
        this.fav = result.liked;
        if (this.hate && this.fav) {
            this.subscribeToSaveResponseHate(
                this.hatredArtistService.hatred(this.artist.id)
            );
        }
        this.loadFavHate(this.artist.id);
        this.loadCountFavHate(this.artist.id);
    }

    private onSaveErrorLiked() {
    }

    loadFavHate(id: number) {
        this.artistService.getFavHate(id).subscribe((favHate) => {
            this.progres = favHate;
        });
    }

    loadCountFavHate(id: number) {
        this.artistService.getNumFavHate(id).subscribe((countFavHate) => {
            this.votesCount = '' + countFavHate;
        });
    }
}
