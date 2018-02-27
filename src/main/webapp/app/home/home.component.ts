import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../shared';
import {Movie} from "../entities/movie/movie.model";
import {MovieService} from "../entities/movie/movie.service";
import {ResponseWrapper} from "../shared/model/response-wrapper.model";

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
    topPelis: String[] = ['Justice League', 'Harry Potter y la camara secreta', 'Distrito 12', 'Fast and Furious 8', 'Coco'];
    topSeries: String[] = ['Doctor Who', 'The Flash', 'Arrow', 'Supergirl', 'Legends of Tomorrow'];
    moviesPending: Movie[];
    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private movieService: MovieService,
        private jhiAlertService: JhiAlertService
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }
    loadPendingMovies() {
        this.movieService.pendingMovies().subscribe(
            (res: ResponseWrapper) => {
                this.moviesPending = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
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
