import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReviewMovieComponent } from './review-movie.component';
import { ReviewMovieDetailComponent } from './review-movie-detail.component';
import { ReviewMoviePopupComponent } from './review-movie-dialog.component';
import { ReviewMovieDeletePopupComponent } from './review-movie-delete-dialog.component';

export const reviewMovieRoute: Routes = [
    {
        path: 'review-movie',
        component: ReviewMovieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.reviewMovie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'review-movie/:id',
        component: ReviewMovieDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.reviewMovie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reviewMoviePopupRoute: Routes = [
    {
        path: 'review-movie-new',
        component: ReviewMoviePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.reviewMovie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'review-movie/:id/edit',
        component: ReviewMoviePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.reviewMovie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'review-movie/:id/delete',
        component: ReviewMovieDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.reviewMovie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
