import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SeriesStatsComponent } from './series-stats.component';
import { SeriesStatsDetailComponent } from './series-stats-detail.component';
import { SeriesStatsPopupComponent } from './series-stats-dialog.component';
import { SeriesStatsDeletePopupComponent } from './series-stats-delete-dialog.component';

export const seriesStatsRoute: Routes = [
    {
        path: 'series-stats',
        component: SeriesStatsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.seriesStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'series-stats/:id',
        component: SeriesStatsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.seriesStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seriesStatsPopupRoute: Routes = [
    {
        path: 'series-stats-new',
        component: SeriesStatsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.seriesStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'series-stats/:id/edit',
        component: SeriesStatsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.seriesStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'series-stats/:id/delete',
        component: SeriesStatsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.seriesStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
