import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import { Series } from './series.model';
import { SeriesService } from './series.service';
import {Artist} from '../artist/artist.model';
import {ResponseWrapper} from '../../shared/model/response-wrapper.model';
import {ArtistService} from '../artist/artist.service';

@Component({
    selector: 'jhi-series-detail',
    templateUrl: './series-detail.component.html'
})
export class SeriesDetailComponent implements OnInit, OnDestroy {
    artists: Artist[];
    director: Artist;
    scripwriter: Artist;
    series: Series;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private seriesService: SeriesService,
        private artistService: ArtistService,
        private route: ActivatedRoute,
        private jhiAlertService: JhiAlertService
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
            this.loadArtist(params['id']);
            this.loadDirector(params['id']);
            this.loadScriptwriter(params['id']);
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
