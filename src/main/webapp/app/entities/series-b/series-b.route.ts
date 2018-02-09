import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SeriesBComponent } from './series-b.component';
import { SeriesBDetailComponent } from './series-b-detail.component';
import { SeriesBPopupComponent } from './series-b-dialog.component';
import { SeriesBDeletePopupComponent } from './series-b-delete-dialog.component';

export const seriesBRoute: Routes = [
    {
        path: 'series-b',
        component: SeriesBComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.seriesB.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'series-b/:id',
        component: SeriesBDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.seriesB.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seriesBPopupRoute: Routes = [
    {
        path: 'series-b-new',
        component: SeriesBPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.seriesB.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'series-b/:id/edit',
        component: SeriesBPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.seriesB.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'series-b/:id/delete',
        component: SeriesBDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.seriesB.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
