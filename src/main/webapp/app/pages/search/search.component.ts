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
    name = 'Thor';
    movies: Movie[];
    date: string;
    countryId = 1;

    constructor(private searchService: SearchService,
                private jhiAlertService: JhiAlertService,
                private eventManager: JhiEventManager,
                private principal: Principal,
                private movieService: MovieService,
                config: NgbRatingConfig) {
        config.max = 5;
    }

    ngOnInit() {

        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
    }

    ngOnDestroy() {

    }

    buscarMovies() {
        let criteria = '';

        if (this.name !== '' ) {
            console.log(this.name);
            criteria += 'name.contains=' + this.name;
        }

        console.log(criteria)
        this.movieService.superQuery(criteria).subscribe(
            (res: ResponseWrapper) => {
                console.log(res);
                this.movies = res.json;
            }
        );
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
