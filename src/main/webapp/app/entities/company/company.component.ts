import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Company } from './company.model';
import { CompanyService } from './company.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {Movie} from '../movie/movie.model';
import {EpisodeNextSeen} from '../../shared/model/EpisodeNextSeen.model';
import {LoginModalService} from '../../shared/login/login-modal.service';
import {MovieService} from '../movie/movie.service';
import {EpisodeService} from '../episode/episode.service';
import {Series, SeriesService} from '../series';
import {Artist, ArtistService} from '../artist';

@Component({
    selector: 'jhi-company',
    styleUrls: ['./index.css'],
    templateUrl: './company.component.html'
})
export class CompanyComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    topPelis: Movie[];
    topSeries: Series[];
    topArtist: Artist[];
    topArtistHate: Artist[];
    moviesPending: Movie[];
    episodePending: EpisodeNextSeen[];
    $: any;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private movieService: MovieService,
        private jhiAlertService: JhiAlertService,
        private episodeService: EpisodeService,
        private seriesService: SeriesService,
        private artistService: ArtistService
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.loadPendingMovies();
        this.registerAuthenticationSuccess();
        this.loadTopMovies();
        this.loadNextEpisodes();
        this.loadTopSeries();
        this.loadTopHatredArtist();
        this.loadTopArtist();
    }
    loadPendingMovies() {
        this.movieService.pendingMovies5().subscribe(
            (res: ResponseWrapper) => {
                this.moviesPending = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    loadTopMovies() {
        this.movieService.topMovies().subscribe(
            (res: ResponseWrapper) => {
                this.topPelis = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    loadTopSeries() {
        this.seriesService.topSeries().subscribe(
            (res: ResponseWrapper) => {
                this.topSeries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        )
    }

    loadTopArtist() {
        this.artistService.topFavoriteArtist().subscribe(
            (res: ResponseWrapper) => {
                this.topArtist = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        )
    }

    loadTopHatredArtist() {
        this.artistService.topHatredArtist().subscribe(
            (res: ResponseWrapper) => {
                this.topArtistHate = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        )
    }

    loadNextEpisodes() {
        this.episodeService.nextEpisodes5().subscribe(
            (res: ResponseWrapper) => {
                this.episodePending = res.json;
                console.log(this.episodePending);
            },
            (res: ResponseWrapper) => this.onError(res.json)
        )
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
