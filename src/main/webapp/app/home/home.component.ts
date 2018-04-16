import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../shared';
import {Movie} from "../entities/movie/movie.model";
import {MovieService} from "../entities/movie/movie.service";
import {ResponseWrapper} from "../shared/model/response-wrapper.model";
import {EpisodeService} from "../entities/episode/episode.service";
import {EpisodeNextSeen} from "../shared/model/EpisodeNextSeen.model";

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    topPelis: Movie;
    topSeries: String[] = ['Doctor Who', 'The Flash', 'Arrow', 'Supergirl'];
    moviesPending: Movie[];
    episodePending: EpisodeNextSeen[];
    $: any;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private movieService: MovieService,
        private jhiAlertService: JhiAlertService,
        private episodeService: EpisodeService
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
