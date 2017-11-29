import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RateMovieComponent } from './rate-movie.component';
import { RateMovieDetailComponent } from './rate-movie-detail.component';
import { RateMoviePopupComponent } from './rate-movie-dialog.component';
import { RateMovieDeletePopupComponent } from './rate-movie-delete-dialog.component';

export const rateMovieRoute: Routes = [
    {
        path: 'rate-movie',
        component: RateMovieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.rateMovie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'rate-movie/:id',
        component: RateMovieDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.rateMovie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rateMoviePopupRoute: Routes = [
    {
        path: 'rate-movie-new',
        component: RateMoviePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.rateMovie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rate-movie/:id/edit',
        component: RateMoviePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.rateMovie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rate-movie/:id/delete',
        component: RateMovieDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.rateMovie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
