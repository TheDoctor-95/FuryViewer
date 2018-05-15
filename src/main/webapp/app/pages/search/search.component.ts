import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager, JhiParseLinks, JhiAlertService} from 'ng-jhipster';

import {Search} from './search.model';
import {SearchService} from './search.service';
import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../../shared';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';
import {MovieService} from '../../entities/movie/movie.service';
import {Movie} from "../../entities/movie/movie.model";
import {Genre, GenreService} from '../../entities/genre';
import {Title} from '@angular/platform-browser';
import {Globals} from "../../shared/globals";
import {Series} from "../../entities/series/series.model";
import {SeriesService} from "../../entities/series/series.service";
import {Artist} from "../../entities/artist/artist.model";
import {ArtistService} from "../../entities/artist/artist.service";

@Component({
    selector: 'jhi-search',
    templateUrl: './search.component.html',
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
export class SearchComponent implements OnInit, OnDestroy {

    currentAccount: any;
    eventSubscriber: Subscription;
    name = '';
    movies: Movie[] = [];
    series: Series[] = [];
    artists: Artist[] = [];
    date: number;
    countryId = 1;
    loading = false;
    actualYear;
    genres: Genre[];
    importando: boolean = false;

    constructor(private searchService: SearchService,
                private jhiAlertService: JhiAlertService,
                private eventManager: JhiEventManager,
                private principal: Principal,
                private movieService: MovieService,
                private genreService: GenreService,
                private seriesService: SeriesService,
                private artistService: ArtistService,
                config: NgbRatingConfig,
                public globals: Globals,
                private titleService: Title) {
        config.max = 5;
        this.actualYear = (new Date()).getFullYear();
    }

    change(multimedia: string) {
        this.globals.multimedia = multimedia;
        if (this.globals.multimedia === 'movie') {
            if ('following' === this.globals.opcio) {
                this.globals.opcio = 'pending';
            }
        }
        if (this.globals.multimedia === 'movie') {
            this.buscarMovies();
        } else if (this.globals.multimedia === 'series') {
            this.buscarSeries()
        }
    }

    ngOnInit() {

        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.loadGenres();
        this.titleService.setTitle("Search - FuryViewer");
    }

    loadGenres() {
        this.genreService.query().subscribe(
            (res: ResponseWrapper) => {
                this.genres = res.json;
                console.log(this.genres);
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnDestroy() {

    }

    buscarMovies() {
        this.loading = true;
        let criteria = '';
        this.movies = [];
        this.series = [];
        this.artists = [];

        if (this.name !== '') {
            console.log(this.name);
            criteria += 'name.contains=' + this.name;
        }

        if (this.date !== 0 && this.date !== undefined && this.date !== null) {
            if (criteria !== '') {
                criteria += '&';
            }
            criteria += 'release_date.greaterThan=' + this.date + '-01-01&release_date.lessThan=' + this.date + '-12-31';
        }

        console.log(criteria)
        this.movieService.superQuery(criteria).subscribe(
            (res: ResponseWrapper) => {
                console.log(res);
                this.loading = false;
                this.movies = res.json;
            }
        );
        if (!this.importando) {
            this.importando = true;
            this.searchService.import(this.name).subscribe(
                (res: Movie) => {
                    this.importando = false;
                });
        }
    }

    buscarSeries() {
        this.loading = true;
        let criteria = '';
        this.movies = [];
        this.series = [];
        this.artists = [];

        if (this.name !== '') {
            console.log(this.name);
            criteria += 'name.contains=' + this.name;
        }

        if (this.date !== 0 && this.date !== undefined && this.date !== null) {
            if (criteria !== '') {
                criteria += '&';
            }
            criteria += 'release_date.greaterThan=' + this.date + '-01-01&release_date.lessThan=' + this.date + '-12-31';
        }

        console.log(criteria)
        this.seriesService.superQuery(criteria).subscribe(
            (res: ResponseWrapper) => {
                console.log(res);
                this.loading = false;
                this.series = res.json;
            }
        );
        if (!this.importando) {
            this.importando = true;
            this.searchService.import(this.name).subscribe(
                (res: Movie) => {
                    this.importando = false;
                });
        }
    }

    buscarArtist() {
        this.loading = true;
        let criteria = '';
        this.movies = [];
        this.series = [];
        this.artists = [];

        if (this.name !== '') {
            console.log(this.name);
            criteria += 'name.contains=' + this.name;
        }

        if (this.date !== 0 && this.date !== undefined && this.date !== null) {
            if (criteria !== '') {
                criteria += '&';
            }
            criteria += 'birthdate.greaterThan=' + this.date + '-01-01&birthdate.lessThan=' + this.date + '-12-31';
        }

        console.log(criteria);

        this.artistService.superQuery(criteria).subscribe(
            (res: ResponseWrapper) => {
                console.log(res);
                this.loading = false;
                this.artists = res.json;
            }
        );
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
