import { Component, OnInit, OnDestroy } from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Artist } from './artist.model';
import { ArtistPopupService } from './artist-popup.service';
import { ArtistService } from './artist.service';
import { Country, CountryService } from '../country';
import { ArtistType, ArtistTypeService } from '../artist-type';
import { Movie, MovieService } from '../movie';
import { ResponseWrapper } from '../../shared';
import {ArtistLimitModel} from '../../shared/model/artistLimit.model';
import {Router} from '@angular/router';
import {Globals} from "../../shared/globals";

@Component({
    selector: 'jhi-artist-dialog',
    templateUrl: './artist-dialog.component.html'
})
export class ArtistDialogComponent implements OnInit {

    artist: Artist;
    isSaving: boolean;

    artistLimitless: ArtistLimitModel[];

    countries: Country[];
    movieId: number;

    artisttypes: ArtistType[];

    route: ActivatedRoute;

    movies: Movie[];
    birthdateDp: any;
    deathdateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private artistService: ArtistService,
        private countryService: CountryService,
        private artistTypeService: ArtistTypeService,
        private movieService: MovieService,
        private eventManager: JhiEventManager,
        public global: Globals,
        private router: Router
    ) {
    }

    ngOnInit() {

        this.loadCompleteCasting(this.global.multimediaId);
    }

    goTo(id: number) {
        console.log(id);
        this.router.navigate(['artist', id]).then(
            () => {
                this.clear();
            }
        )
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.artist.id !== undefined) {
            this.subscribeToSaveResponse(
                this.artistService.update(this.artist));
        } else {
            this.subscribeToSaveResponse(
                this.artistService.create(this.artist));
        }
    }

    loadCompleteCasting(id: number) {
        if (this.global.multimedia === 'movie') {
            this.movieService.findActorsLimitless(id).subscribe(
                (res: ResponseWrapper) => {
                    this.artistLimitless = res.json;
                },
                (res: ResponseWrapper) => this.onError(res.json)
            );
        }
    }

    private subscribeToSaveResponse(result: Observable<Artist>) {
        result.subscribe((res: Artist) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Artist) {
        this.eventManager.broadcast({ name: 'artistListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCountryById(index: number, item: Country) {
        return item.id;
    }

    trackArtistTypeById(index: number, item: ArtistType) {
        return item.id;
    }

    trackMovieById(index: number, item: Movie) {
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
    selector: 'jhi-artist-popup',
    template: ''
})
export class ArtistPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private artistPopupService: ArtistPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.artistPopupService
                    .open(ArtistDialogComponent as Component, params['id']);
            } else {
                this.artistPopupService
                    .open(ArtistDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
