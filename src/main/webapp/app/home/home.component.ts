import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../shared';
import {Movie} from '../entities/movie/movie.model';
import {MovieService} from '../entities/movie/movie.service';
import {ResponseWrapper} from '../shared/model/response-wrapper.model';
import {EpisodeService} from '../entities/episode/episode.service';
import {EpisodeNextSeen} from '../shared/model/EpisodeNextSeen.model';
import {Company} from '../entities/company/company.model';
import {CompanyService} from '../entities/company/company.service';
import {Subscription} from 'rxjs/Subscription';
import {Router, RouterLink} from '@angular/router';
import {CountryService} from '../entities/country';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    companies: Company[];
    currentAccount: any;
    eventSubscriber: Subscription;
    modalRef: NgbModalRef;
    numFavs: number;
    numHatred: number;
    numArtist: number;
    numMovies: number;
    numSeries: number;
    numUsers: number;

    constructor(
        private companyService: CompanyService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private loginModalService: LoginModalService,
        private principal: Principal,
        private homeService: CountryService,
        private router: Router
    ) {
        this.numFavs = 0;
        this.numHatred = 0;
        this.numArtist = 0;
        this.numMovies = 0;
        this.numSeries = 0;
        this.numUsers = 0;
    }

    loadAll() {
        this.companyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.companies = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.loadAbsoluteFav();
        this.loadAbsoluteHate();
        this.loadAbsoluteArtist();
        this.loadAbsoluteMovie();
        this.loadAbsoluteSeries();
        this.loadAbsoluteUser();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanies();
        if (this.isAuthenticated()) {
            this.router.navigate(['/home'])
        }
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Company) {
        return item.id;
    }
    registerChangeInCompanies() {
        this.eventSubscriber = this.eventManager.subscribe('companyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    loadAbsoluteFav() {
        this.homeService.getAbsoluteTotalFav().subscribe((favourites) => {
            this.numFavs = favourites;
        });
    }

    loadAbsoluteHate() {
        this.homeService.getAbsoluteTotalHatred().subscribe((hatreds) => {
            this.numHatred = hatreds;
        });
    }

    loadAbsoluteArtist() {
        this.homeService.getAbsoluteTotalArtist().subscribe((artists) => {
            this.numArtist = artists;
        });
    }

    loadAbsoluteMovie() {
        this.homeService.getAbsoluteTotalMovie().subscribe((movies) => {
            this.numMovies = movies;
        });
    }

    loadAbsoluteSeries() {
        this.homeService.getAbsoluteTotalSeries().subscribe((series) => {
            this.numSeries = series;
        });
    }

    loadAbsoluteUser() {
        this.homeService.getAbsoluteTotalUser().subscribe((users) => {
            this.numUsers = users;
        });
    }
}
