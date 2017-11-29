import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { HatredSeriesComponent } from './hatred-series.component';
import { HatredSeriesDetailComponent } from './hatred-series-detail.component';
import { HatredSeriesPopupComponent } from './hatred-series-dialog.component';
import { HatredSeriesDeletePopupComponent } from './hatred-series-delete-dialog.component';

export const hatredSeriesRoute: Routes = [
    {
        path: 'hatred-series',
        component: HatredSeriesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hatred-series/:id',
        component: HatredSeriesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hatredSeriesPopupRoute: Routes = [
    {
        path: 'hatred-series-new',
        component: HatredSeriesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hatred-series/:id/edit',
        component: HatredSeriesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hatred-series/:id/delete',
        component: HatredSeriesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
