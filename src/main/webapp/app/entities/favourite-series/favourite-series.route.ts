import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FavouriteSeriesComponent } from './favourite-series.component';
import { FavouriteSeriesDetailComponent } from './favourite-series-detail.component';
import { FavouriteSeriesPopupComponent } from './favourite-series-dialog.component';
import { FavouriteSeriesDeletePopupComponent } from './favourite-series-delete-dialog.component';

export const favouriteSeriesRoute: Routes = [
    {
        path: 'favourite-series',
        component: FavouriteSeriesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'favourite-series/:id',
        component: FavouriteSeriesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const favouriteSeriesPopupRoute: Routes = [
    {
        path: 'favourite-series-new',
        component: FavouriteSeriesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'favourite-series/:id/edit',
        component: FavouriteSeriesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'favourite-series/:id/delete',
        component: FavouriteSeriesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
