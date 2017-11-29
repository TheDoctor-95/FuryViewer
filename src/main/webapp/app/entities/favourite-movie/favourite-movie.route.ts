import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FavouriteMovieComponent } from './favourite-movie.component';
import { FavouriteMovieDetailComponent } from './favourite-movie-detail.component';
import { FavouriteMoviePopupComponent } from './favourite-movie-dialog.component';
import { FavouriteMovieDeletePopupComponent } from './favourite-movie-delete-dialog.component';

export const favouriteMovieRoute: Routes = [
    {
        path: 'favourite-movie',
        component: FavouriteMovieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteMovie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'favourite-movie/:id',
        component: FavouriteMovieDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteMovie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const favouriteMoviePopupRoute: Routes = [
    {
        path: 'favourite-movie-new',
        component: FavouriteMoviePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteMovie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'favourite-movie/:id/edit',
        component: FavouriteMoviePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteMovie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'favourite-movie/:id/delete',
        component: FavouriteMovieDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteMovie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
