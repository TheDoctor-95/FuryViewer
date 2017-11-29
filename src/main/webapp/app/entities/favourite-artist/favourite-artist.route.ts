import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FavouriteArtistComponent } from './favourite-artist.component';
import { FavouriteArtistDetailComponent } from './favourite-artist-detail.component';
import { FavouriteArtistPopupComponent } from './favourite-artist-dialog.component';
import { FavouriteArtistDeletePopupComponent } from './favourite-artist-delete-dialog.component';

export const favouriteArtistRoute: Routes = [
    {
        path: 'favourite-artist',
        component: FavouriteArtistComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteArtist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'favourite-artist/:id',
        component: FavouriteArtistDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteArtist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const favouriteArtistPopupRoute: Routes = [
    {
        path: 'favourite-artist-new',
        component: FavouriteArtistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteArtist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'favourite-artist/:id/edit',
        component: FavouriteArtistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteArtist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'favourite-artist/:id/delete',
        component: FavouriteArtistDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.favouriteArtist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
