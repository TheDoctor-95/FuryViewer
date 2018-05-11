import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ArtistComponent } from './artist.component';
import { ArtistDetailComponent } from './artist-detail.component';
import { ArtistPopupComponent } from './artist-dialog.component';
import { ArtistDeletePopupComponent } from './artist-delete-dialog.component';

export const artistRoute: Routes = [
    {
        path: 'artist',
        component: ArtistComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'artist/:id',
        component: ArtistDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const artistPopupRoute: Routes = [
    {
        path: 'casting-complete',
        component: ArtistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artist/:id/edit',
        component: ArtistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artist/:id/delete',
        component: ArtistDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
