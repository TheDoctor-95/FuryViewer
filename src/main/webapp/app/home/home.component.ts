import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../shared';
import {Movie} from "../entities/movie/movie.model";
import {MovieService} from "../entities/movie/movie.service";
import {ResponseWrapper} from "../shared/model/response-wrapper.model";
import {EpisodeService} from "../entities/episode/episode.service";
import {EpisodeNextSeen} from "../shared/model/EpisodeNextSeen.model";
import {Company} from "../entities/company/company.model";
import {CompanyService} from "../entities/company/company.service";
import {Subscription} from "rxjs/Subscription";

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

    constructor(
        private companyService: CompanyService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private loginModalService: LoginModalService,
        private principal: Principal
    ) {
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
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCompanies();
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



}
