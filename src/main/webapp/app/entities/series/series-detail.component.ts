import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import { Series } from './series.model';
import { SeriesService } from './series.service';
import {Artist} from '../artist/artist.model';
import {ResponseWrapper} from '../../shared/model/response-wrapper.model';
import {ArtistService} from '../artist/artist.service';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';
import {FavouriteMovie} from '../favourite-movie/favourite-movie.model';
import {HatredMovie} from '../hatred-movie/hatred-movie.model';
import {FavouriteSeries} from '../favourite-series/favourite-series.model';
import {HatredSeries} from '../hatred-series/hatred-series.model';
import {SeasonService} from "../season/season.service";
import {EpisodeService} from "../episode/episode.service";
import {EpisodeSeasonModel} from "../../shared/model/EpisodeSeason.model";

@Component({
    selector: 'jhi-series-detail',
    templateUrl: './series-detail.component.html',
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
export class SeriesDetailComponent implements OnInit, OnDestroy {
    artists: Artist[];
    director: Artist;
    scripwriter: Artist;
    series: Series;
    fav: FavouriteMovie;
    hate: HatredMovie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    seasons: number[] = [1,2,3,4,5];
    chapters: EpisodeSeasonModel;

    constructor(
        private eventManager: JhiEventManager,
        private seriesService: SeriesService,
        private artistService: ArtistService,
        private route: ActivatedRoute,
        private jhiAlertService: JhiAlertService,
        private seasonService: SeasonService,
        private episodeService: EpisodeService,
        config: NgbRatingConfig
) {
    this.director = new Artist();
    this.director.name="";
    this.scripwriter = new Artist();
    this.scripwriter.name="";
    config.max = 5;
        this.fav = new FavouriteSeries();
        this.fav.liked=false;
        this.hate = new HatredSeries();
        this.hate.hated=false;
}

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
            this.loadArtist(params['id']);
            this.loadDirector(params['id']);
            this.loadScriptwriter(params['id']);
            this.loadSeasons(params['id']);
            this.loadEpisodes(2);
        });
        this.registerChangeInSeries();

    }

    load(id) {
        this.seriesService.find(id).subscribe((series) => {
            this.series = series;
        });
    }
    loadArtist(id: number) {
        this.artistService.seriesActorsQuery(id).subscribe(
            (res: ResponseWrapper) => {
                this.artists = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    loadSeasons(id: number){
        this.seasonService.findSeasons(id).subscribe(
            (res: ResponseWrapper) => {
                this.seasons = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)

        )
    }

    loadEpisodes(id: number){
        this.episodeService.findEpisodeBySeasonId(id).subscribe(
            (res: ResponseWrapper) => {
                this.chapters = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)

        )
    }

    loadDirector(id: number){
        this.artistService.seriesDirectorQuery(id).subscribe((artist) => {
            this.director = artist;
        });
    }
    loadScriptwriter(id: number){
        this.artistService.seriesScriptWriterQuery(id).subscribe((artist) => {
            this.scripwriter = artist;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSeries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'seriesListModification',
            (response) => this.load(this.series.id)
        );
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
