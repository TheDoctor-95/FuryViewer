import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MovieComponent } from './movie.component';
import { MovieDetailComponent } from './movie-detail.component';
import { MoviePopupComponent } from './movie-dialog.component';
import { MovieDeletePopupComponent } from './movie-delete-dialog.component';

export const movieRoute: Routes = [
    {
        path: 'movie',
        component: MovieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.movie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'movie/:id',
        component: MovieDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.movie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const moviePopupRoute: Routes = [
    {
        path: 'movie-pending',
        component: MoviePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.movie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'movie/:id/edit',
        component: MoviePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.movie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'movie/:id/delete',
        component: MovieDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.movie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
