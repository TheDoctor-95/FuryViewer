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
import {FavouriteSeries} from '../favourite-series/favourite-series.model';
import {HatredSeries} from '../hatred-series/hatred-series.model';
import {SeasonService} from '../season/season.service';
import {EpisodeService} from '../episode/episode.service';
import {EpisodeSeasonModel} from '../../shared/model/EpisodeSeason.model';
import {FavouriteSeriesService} from '../favourite-series/favourite-series.service';
import {Observable} from 'rxjs/Observable';
import {HatredSeriesService} from '../hatred-series/hatred-series.service';
import {ChapterSeen} from '../chapter-seen/chapter-seen.model';
import {ChapterSeenService} from '../chapter-seen/chapter-seen.service';
import {SeriesStats, SeriesStatsService} from '../series-stats';
import {SocialService} from '../social';
import {Social} from '../social/social.model';
import {RateSeries, RateSeriesService} from '../rate-series';
import {ReviewSeries} from '../review-series';
import {ReviewSeriesService} from '../review-series';
import { Title } from '@angular/platform-browser';

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
    chapters: EpisodeSeasonModel[];
    actualSeason: number;
    idSeasonActual: number;
    stats: string;
    marks: Social[];
    rateUser: RateSeries;
    media: string;
    progres: number;
    votesCount: string;
    numEpisodes: number;
    numEpisodesSeen: number;
    reviewSeries: ReviewSeries[];
    newComent: ReviewSeries;
    actualDate: any;

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
        private chapterSeenService: ChapterSeenService,
        private seriesStatService: SeriesStatsService,
        private socialService: SocialService,
        private rateSeriesService: RateSeriesService,
        private reviewSeriesService: ReviewSeriesService,
        private titleService: Title
) {
    this.director = new Artist();
    this.director.name = '';
    this.scripwriter = new Artist();
    this.scripwriter.name = '';
    this.stats = '';
    config.max = 5;
    this.fav = false;
    this.hate = false;
    this.rateUser = new RateSeries();
    this.rateUser.rate = 0;
    this.media = '0';
    this.progres = 0;
    this.votesCount = '0';
    this.numEpisodes = 0;
    this.numEpisodesSeen = 0;
    this.newComent = new ReviewSeries();
    this.newComent.title = '';
    this.newComent.review = '';
    this.actualDate = new Date();
}

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
            this.loadArtist(params['id']);
            this.loadDirector(params['id']);
            this.loadScriptwriter(params['id']);
            this.loadSeasons(params['id']);
            this.loadActualSeason(params['id']);
            this.loadState(params['id']);
            this.loadHate(params['id']);
            this.loadFav(params['id']);
            this.loadMarks(params['id']);
            this.loadRateUser(params['id']);
            this.loadMediumMark(params['id']);
            this.loadFavHate(params['id']);
            this.loadCountFavHate(params['id']);
            this.loadNumEpisodesSeen();
            this.loadNumEpisodes();
            this.loadReviews(params['id']);
        });
        this.registerChangeInSeries();

    }

    load(id) {
        this.seriesService.find(id).subscribe((series) => {
            this.series = series;
            this.titleService.setTitle(this.series.name+" - FuryViewer");
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

    loadMarks(id: number) {
        this.socialService.seriesMarks(id).subscribe(
            (res: ResponseWrapper) => {
                this.marks = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    loadSeasons(id: number) {
        this.seasonService.findSeasons(id).subscribe(
            (res: ResponseWrapper) => {
                this.seasons = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)

        )
    }

    loadEpisodes(id: number) {
        this.idSeasonActual = id;
        this.seasonNumberFind(id);
        this.episodeService.findEpisodeBySeasonId(id).subscribe(
            (res: ResponseWrapper) => {
                this.chapters = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)

        )
        this.loadNumEpisodesSeen();
        this.loadNumEpisodes();
    }

    loadActualSeason(id: number) {
        this.seasonService.actualSeason(id).subscribe(
            (res: number) => {
                this.loadEpisodes(res);
            },
            (res: ResponseWrapper) => this.onError(res.json)
        )
    }

    markChapter(id: number) {
        this.subscribeToSaveResponseSeen(
            this.chapterSeenService.createId(id)
        );
        this.loadNumEpisodesSeen();
        this.loadNumEpisodes();
    }

    private subscribeToSaveResponseSeen(result: Observable<ChapterSeen>) {
        result.subscribe((res: ChapterSeen) =>
            this.onSaveSuccessSeen(res), (res: Response) => this.onSaveErrorSeen());
    }

    private onSaveSuccessSeen(result: ChapterSeen) {
        this.eventManager.broadcast({ name: 'chapterSeenListModification', content: 'OK'});
        this.loadEpisodes(this.idSeasonActual);
    }

    private onSaveErrorSeen() {

    }
    seasonNumberFind(id: number) {
        let i = 1;
        for (const id2 of this.seasons) {
            if (id === id2) {
                this.actualSeason = i;
                return null;
            }
            i++;
        }
    }

    loadDirector(id: number) {
        this.artistService.seriesDirectorQuery(id).subscribe((artist) => {
            this.director = artist;
        });
    }

    loadScriptwriter(id: number) {
        this.artistService.seriesScriptWriterQuery(id).subscribe((artist) => {
            this.scripwriter = artist;
        });
    }

    previousState() {
        window.history.back();
    }

    loadFav(id: number) {
        this.favouriteSeriesService.getIfLiked(id).subscribe((fav) => {
            this.fav = fav.like;
        })
    }
    loadHate(id: number) {
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

    like() {
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
        this.fav = result.liked;
        if (this.hate && this.fav) {
            this.subscribeToSaveResponseHate(
                this.hatredSeriesService.hatred(this.series.id)
            );
        }
        this.loadFavHate(this.series.id);
        this.loadCountFavHate(this.series.id);
    }

    private onSaveErrorLike() {
    }

    hated() {
        this.subscribeToSaveResponseHate(
            this.hatredSeriesService.hatred(this.series.id)
        );
    }

    private subscribeToSaveResponseHate(result: Observable<HatredSeries>) {
        result.subscribe((res: HatredSeries) =>
            this.onSaveSuccessHatred(res), (res: Response) => this.onSaveErrorHatred());
    }

    private onSaveSuccessHatred(result: HatredSeries) {
        this.eventManager.broadcast({ name: 'hatredSeriesListModification', content: 'OK'});
        this.hate = result.hated;
        if (this.hate && this.fav) {
            this.subscribeToSaveResponseLike(
                this.favouriteSeriesService.favourite(this.series.id)
            );
        }
        this.loadFavHate(this.series.id);
        this.loadCountFavHate(this.series.id);
    }

    private onSaveErrorHatred() {
    }

    stat(stat: string) {
        this.subscribeToSaveResponseStat(
            this.seriesStatService.stat(this.series.id, stat)
        );
    }

    private subscribeToSaveResponseStat(result: Observable<SeriesStats>) {
        result.subscribe((res: SeriesStats) =>
        this.onSaveSuccessStat(res), (res: Response) => this.onSaveErrorStat());
    }

    private onSaveSuccessStat(result: SeriesStats) {
        this.eventManager.broadcast({name: 'seriesStatsListModification', content: 'OK'});
        this.loadState(this.series.id);
    }

    private onSaveErrorStat() {
    }

    loadState(id: number) {
        this.seriesStatService.getState(id).subscribe((stat) => {
            this.stats = stat.url;
        });
    }

    rate() {
        this.subscribeToSaveResponseRate(
            this.rateSeriesService.rate(this.series.id, this.rateUser.rate)
        );
    }

    private subscribeToSaveResponseRate(result: Observable<RateSeries>) {
        result.subscribe((res: RateSeries) =>
            this.onSaveSuccessRate(res), (res: Response) => this.onSaveErrorRate());
    }

    private onSaveSuccessRate(result: RateSeries) {
        this.eventManager.broadcast({ name: 'rateSeriesListModification', content: 'OK'});
        this.rateUser = result;
        this.loadMediumMark(this.series.id);
    }

    private onSaveErrorRate() {

    }

    loadRateUser(id: number) {
        this.rateSeriesService.markSeriesUser(id).subscribe((rateSeries) => {
            this.rateUser = rateSeries;
        });
    }

    loadMediumMark(id: number) {
        this.rateSeriesService.mediaSeries(id).subscribe((rateSeries) => {
            const ourMark = rateSeries.toPrecision(2);
            this.media = ourMark;
        });
    }

    loadFavHate(id: number) {
        this.seriesService.getFavHate(id).subscribe((favHate) => {
            this.progres = favHate;
        });
    }

    loadCountFavHate(id: number) {
        this.seriesService.getNumFavHate(id).subscribe((countFavHate) => {
            this.votesCount = '' + countFavHate;
        });
    }

    markSeason() {
        this.subscribeToSaveResponseMarkSeason(
            this.seasonService.getMarkSeason(this.idSeasonActual)
        );
    }

    private subscribeToSaveResponseMarkSeason(result: Observable<Boolean>) {
        result.subscribe((res: Boolean) =>
            this.onSaveSuccessMarkSeason(res), (res: Response) => this.onSaveErrorMarkSeason());
    }

    private onSaveSuccessMarkSeason(result: Boolean) {
        this.eventManager.broadcast({ name: 'markSeasonListModification', content: 'OK'});
        this.loadEpisodes(this.idSeasonActual);
        this.loadNumEpisodesSeen();
        this.loadNumEpisodes();
    }

    private onSaveErrorMarkSeason() {
    }

    loadNumEpisodesSeen() {
        this.seasonService.getNumEpisodesSeen(this.idSeasonActual).subscribe((numSeen) => {
            this.numEpisodesSeen = numSeen;
        });
    }

    loadNumEpisodes() {
        this.seasonService.getNumEpisodes(this.idSeasonActual).subscribe((numEp) => {
            this.numEpisodes = numEp;
        });
    }

    loadReviews(id: number) {
        this.reviewSeriesService.findSeriesReviews(id).subscribe((res: ResponseWrapper) => {
                this.reviewSeries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    comentar() {
        if (this.newComent.title !== '' && this.newComent.review !== '') {
            this.newComent.series = this.series;
            this.subscribeToSaveResponseReview(
                this.reviewSeriesService.create(this.newComent)
            );
        }
    }

    private subscribeToSaveResponseReview(result: Observable<ReviewSeries>) {
        result.subscribe((res: ReviewSeries) =>
            this.onSaveSuccessReview(res), (res: Response) => this.onSaveErrorReview());

    }

    private onSaveSuccessReview(result: ReviewSeries) {
        this.eventManager.broadcast({ name: 'reviewSeriesListModification', content: 'OK'});
        this.newComent.title = '';
        this.newComent.review = '';
        this.loadReviews(this.series.id);
    }

    private onSaveErrorReview() {
    }
}
