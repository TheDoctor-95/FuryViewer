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
import {FavouriteSeriesService} from "../favourite-series/favourite-series.service";
import {Observable} from "rxjs/Observable";
import {HatredSeriesService} from "../hatred-series/hatred-series.service";
import {ChapterSeen} from "../chapter-seen/chapter-seen.model";
import {ChapterSeenService} from "../chapter-seen/chapter-seen.service";

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
    fav: boolean;
    hate: boolean;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    seasons: number[];
    chapters: EpisodeSeasonModel;
    actualSeason: number;
    idSeasonActual: number;
    constructor(
        private eventManager: JhiEventManager,
        private seriesService: SeriesService,
        private artistService: ArtistService,
        private route: ActivatedRoute,
        private jhiAlertService: JhiAlertService,
        private seasonService: SeasonService,
        private episodeService: EpisodeService,
        config: NgbRatingConfig,
        private favouriteSeriesService: FavouriteSeriesService,
        private hatredSeriesService: HatredSeriesService,
        private chapterSeenService: ChapterSeenService
) {
    this.director = new Artist();
    this.director.name="";
    this.scripwriter = new Artist();
    this.scripwriter.name="";
    config.max = 5;
        this.fav=false;

        this.hate=false;
}

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
            this.loadArtist(params['id']);
            this.loadDirector(params['id']);
            this.loadScriptwriter(params['id']);
            this.loadSeasons(params['id']);
            this.loadActualSeason(params['id']);

            this.loadHate(params['id']);
            this.loadFav(params['id']);

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
        this.idSeasonActual=id;
        this.seasonNumberFind(id);
        this.episodeService.findEpisodeBySeasonId(id).subscribe(
            (res: ResponseWrapper) => {
                this.chapters = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)

        )
    }

    loadActualSeason(id:number){
        this.seasonService.actualSeason(id).subscribe(
            (res: number) => {
                this.loadEpisodes(res);
            },
            (res: ResponseWrapper) => this.onError(res.json)
        )
    }

    markChapter(id: number){
        this.subscribeToSaveResponseSeen(
            this.chapterSeenService.createId(id)

    );
    }

    private subscribeToSaveResponseSeen(result: Observable<ChapterSeen>) {
        result.subscribe((res: ChapterSeen) =>
            this.onSaveSuccessSeen(res), (res: Response) => this.onSaveErrorSeen());
        this.loadEpisodes(this.idSeasonActual);
    }

    private onSaveSuccessSeen(result: ChapterSeen) {
        this.eventManager.broadcast({ name: 'chapterSeenListModification', content: 'OK'});

    }

    private onSaveErrorSeen() {

    }
    seasonNumberFind(id: number){
        let i=1;
        for (let id2 of this.seasons) {
            if(id==id2){
                this.actualSeason=i;
                return null;
            }
            i++;
        }

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

    loadFav(id: number){
        this.favouriteSeriesService.getIfLiked(id).subscribe((fav) => {
            this.fav=fav.like;
        })
    }
    loadHate(id:number) {
        this.hatredSeriesService.getIfHated(id).subscribe((hate) => {
            this.hate = hate.like;
        });
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

    like(){
    this.subscribeToSaveResponseLike(
        this.favouriteSeriesService.favourite(this.series.id)
    );
}

    private subscribeToSaveResponseLike(result: Observable<FavouriteSeries>) {
        result.subscribe((res: FavouriteSeries) =>
            this.onSaveSuccessLike(res), (res: Response) => this.onSaveErrorLike());

    }

    private onSaveSuccessLike(result: FavouriteSeries) {
        this.eventManager.broadcast({ name: 'favouriteSeriesListModification', content: 'OK'});
        this.loadFav(this.series.id);
    }

    private onSaveErrorLike() {
    }

}
