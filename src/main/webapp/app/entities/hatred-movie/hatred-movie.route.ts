import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { HatredMovieComponent } from './hatred-movie.component';
import { HatredMovieDetailComponent } from './hatred-movie-detail.component';
import { HatredMoviePopupComponent } from './hatred-movie-dialog.component';
import { HatredMovieDeletePopupComponent } from './hatred-movie-delete-dialog.component';

export const hatredMovieRoute: Routes = [
    {
        path: 'hatred-movie',
        component: HatredMovieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredMovie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hatred-movie/:id',
        component: HatredMovieDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredMovie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hatredMoviePopupRoute: Routes = [
    {
        path: 'hatred-movie-new',
        component: HatredMoviePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredMovie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hatred-movie/:id/edit',
        component: HatredMoviePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredMovie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hatred-movie/:id/delete',
        component: HatredMovieDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredMovie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
