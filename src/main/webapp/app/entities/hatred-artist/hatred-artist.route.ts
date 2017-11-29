import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { HatredArtistComponent } from './hatred-artist.component';
import { HatredArtistDetailComponent } from './hatred-artist-detail.component';
import { HatredArtistPopupComponent } from './hatred-artist-dialog.component';
import { HatredArtistDeletePopupComponent } from './hatred-artist-delete-dialog.component';

export const hatredArtistRoute: Routes = [
    {
        path: 'hatred-artist',
        component: HatredArtistComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredArtist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hatred-artist/:id',
        component: HatredArtistDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredArtist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hatredArtistPopupRoute: Routes = [
    {
        path: 'hatred-artist-new',
        component: HatredArtistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredArtist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hatred-artist/:id/edit',
        component: HatredArtistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredArtist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hatred-artist/:id/delete',
        component: HatredArtistDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.hatredArtist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
