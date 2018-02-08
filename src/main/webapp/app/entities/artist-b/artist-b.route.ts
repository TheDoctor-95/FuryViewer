import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ArtistBComponent } from './artist-b.component';
import { ArtistBDetailComponent } from './artist-b-detail.component';
import { ArtistBPopupComponent } from './artist-b-dialog.component';
import { ArtistBDeletePopupComponent } from './artist-b-delete-dialog.component';

export const artistBRoute: Routes = [
    {
        path: 'artist-b',
        component: ArtistBComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artistB.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'artist-b/:id',
        component: ArtistBDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artistB.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const artistBPopupRoute: Routes = [
    {
        path: 'artist-b-new',
        component: ArtistBPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artistB.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artist-b/:id/edit',
        component: ArtistBPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artistB.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artist-b/:id/delete',
        component: ArtistBDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artistB.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
