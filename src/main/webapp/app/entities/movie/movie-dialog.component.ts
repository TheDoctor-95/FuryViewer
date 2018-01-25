import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Movie } from './movie.model';
import { MoviePopupService } from './movie-popup.service';
import { MovieService } from './movie.service';
import { Artist, ArtistService } from '../artist';
import { Company, CompanyService } from '../company';
import { Genre, GenreService } from '../genre';
import { Country, CountryService } from '../country';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-movie-dialog',
    templateUrl: './movie-dialog.component.html'
})
export class MovieDialogComponent implements OnInit {

    movie: Movie;
    isSaving: boolean;

    artists: Artist[];

    companies: Company[];

    genres: Genre[];

    countries: Country[];
    releaseDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private movieService: MovieService,
        private artistService: ArtistService,
        private companyService: CompanyService,
        private genreService: GenreService,
        private countryService: CountryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.artistService.query()
            .subscribe((res: ResponseWrapper) => { this.artists = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.genreService.query()
            .subscribe((res: ResponseWrapper) => { this.genres = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.countryService.query()
            .subscribe((res: ResponseWrapper) => { this.countries = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.movie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.movieService.update(this.movie));
        } else {
            this.subscribeToSaveResponse(
                this.movieService.create(this.movie));
        }
    }

    private subscribeToSaveResponse(result: Observable<Movie>) {
        result.subscribe((res: Movie) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Movie) {
        this.eventManager.broadcast({ name: 'movieListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackArtistById(index: number, item: Artist) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackGenreById(index: number, item: Genre) {
        return item.id;
    }

    trackCountryById(index: number, item: Country) {
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
    selector: 'jhi-movie-popup',
    template: ''
})
export class MoviePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private moviePopupService: MoviePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.moviePopupService
                    .open(MovieDialogComponent as Component, params['id']);
            } else {
                this.moviePopupService
                    .open(MovieDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
