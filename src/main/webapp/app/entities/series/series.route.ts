import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SeriesComponent } from './series.component';
import { SeriesDetailComponent } from './series-detail.component';
import { SeriesPopupComponent } from './series-dialog.component';
import { SeriesDeletePopupComponent } from './series-delete-dialog.component';

export const seriesRoute: Routes = [
    {
        path: 'series',
        component: SeriesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.series.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'series/:id',
        component: SeriesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.series.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seriesPopupRoute: Routes = [
    {
        path: 'series-new',
        component: SeriesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.series.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'series/:id/edit',
        component: SeriesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.series.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'series/:id/delete',
        component: SeriesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.series.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
