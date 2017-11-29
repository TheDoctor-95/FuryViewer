import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReviewSeriesComponent } from './review-series.component';
import { ReviewSeriesDetailComponent } from './review-series-detail.component';
import { ReviewSeriesPopupComponent } from './review-series-dialog.component';
import { ReviewSeriesDeletePopupComponent } from './review-series-delete-dialog.component';

export const reviewSeriesRoute: Routes = [
    {
        path: 'review-series',
        component: ReviewSeriesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.reviewSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'review-series/:id',
        component: ReviewSeriesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.reviewSeries.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reviewSeriesPopupRoute: Routes = [
    {
        path: 'review-series-new',
        component: ReviewSeriesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.reviewSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'review-series/:id/edit',
        component: ReviewSeriesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.reviewSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'review-series/:id/delete',
        component: ReviewSeriesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.reviewSeries.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
