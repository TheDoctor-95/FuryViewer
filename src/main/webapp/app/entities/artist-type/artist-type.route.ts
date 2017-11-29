import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ArtistTypeComponent } from './artist-type.component';
import { ArtistTypeDetailComponent } from './artist-type-detail.component';
import { ArtistTypePopupComponent } from './artist-type-dialog.component';
import { ArtistTypeDeletePopupComponent } from './artist-type-delete-dialog.component';

export const artistTypeRoute: Routes = [
    {
        path: 'artist-type',
        component: ArtistTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artistType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'artist-type/:id',
        component: ArtistTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artistType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const artistTypePopupRoute: Routes = [
    {
        path: 'artist-type-new',
        component: ArtistTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artistType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artist-type/:id/edit',
        component: ArtistTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artistType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'artist-type/:id/delete',
        component: ArtistTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.artistType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
