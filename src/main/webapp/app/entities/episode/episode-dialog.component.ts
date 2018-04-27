import { Component, OnInit, OnDestroy } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Episode } from './episode.model';
import { EpisodePopupService } from './episode-popup.service';
import { EpisodeService } from './episode.service';
import { Season, SeasonService } from '../season';
import { Artist, ArtistService } from '../artist';
import { ResponseWrapper } from '../../shared';
import {EpisodeNextSeen} from '../../shared/model/EpisodeNextSeen.model';

@Component({
    selector: 'jhi-episode-dialog',
    templateUrl: './episode-dialog.component.html'
})
export class EpisodeDialogComponent implements OnInit {

    isSaving: boolean;
    seasons: Season[];
    artists: Artist[];
    episodePending: EpisodeNextSeen[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private episodeService: EpisodeService,
        private seasonService: SeasonService,
        private artistService: ArtistService,
        private eventManager: JhiEventManager,
        public router: Router
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.seasonService.query()
            .subscribe((res: ResponseWrapper) => { this.seasons = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.artistService.query()
            .subscribe((res: ResponseWrapper) => { this.artists = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.loadNextEpisodes();
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    goTo(id: number) {
        this.router.navigate(['/series', id]);
    }

    loadNextEpisodes() {
        this.episodeService.nextEpisodes().subscribe(
            (res: ResponseWrapper) => {
                this.episodePending = res.json;
                console.log(this.episodePending);
            },
            (res: ResponseWrapper) => this.onError(res.json)
        )
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSeasonById(index: number, item: Season) {
        return item.id;
    }

    trackArtistById(index: number, item: Artist) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-episode-popup',
    template: ''
})
export class EpisodePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private episodePopupService: EpisodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.episodePopupService
                    .open(EpisodeDialogComponent as Component, params['id']);
            } else {
                this.episodePopupService
                    .open(EpisodeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
