import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RateSeriesComponent } from './rate-series.component';
import { RateSeriesDetailComponent } from './rate-series-detail.component';
import { RateSeriesPopupComponent } from './rate-series-dialog.component';
import { RateSeriesDeletePopupComponent } from './rate-series-delete-dialog.component';

export const rateSeriesRoute: Routes = [
    {
        path: 'rate-series',
        component: RateSeriesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.rateSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'rate-series/:id',
        component: RateSeriesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.rateSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rateSeriesPopupRoute: Routes = [
    {
        path: 'rate-series-new',
        component: RateSeriesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.rateSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rate-series/:id/edit',
        component: RateSeriesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.rateSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rate-series/:id/delete',
        component: RateSeriesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.rateSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
