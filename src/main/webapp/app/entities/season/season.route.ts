import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SeasonComponent } from './season.component';
import { SeasonDetailComponent } from './season-detail.component';
import { SeasonPopupComponent } from './season-dialog.component';
import { SeasonDeletePopupComponent } from './season-delete-dialog.component';

export const seasonRoute: Routes = [
    {
        path: 'season',
        component: SeasonComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.season.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'season/:id',
        component: SeasonDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.season.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seasonPopupRoute: Routes = [
    {
        path: 'season-new',
        component: SeasonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.season.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'season/:id/edit',
        component: SeasonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.season.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'season/:id/delete',
        component: SeasonDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.season.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
