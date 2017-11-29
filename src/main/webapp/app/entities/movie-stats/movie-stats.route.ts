import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MovieStatsComponent } from './movie-stats.component';
import { MovieStatsDetailComponent } from './movie-stats-detail.component';
import { MovieStatsPopupComponent } from './movie-stats-dialog.component';
import { MovieStatsDeletePopupComponent } from './movie-stats-delete-dialog.component';

export const movieStatsRoute: Routes = [
    {
        path: 'movie-stats',
        component: MovieStatsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.movieStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'movie-stats/:id',
        component: MovieStatsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.movieStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const movieStatsPopupRoute: Routes = [
    {
        path: 'movie-stats-new',
        component: MovieStatsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.movieStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'movie-stats/:id/edit',
        component: MovieStatsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.movieStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'movie-stats/:id/delete',
        component: MovieStatsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.movieStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
