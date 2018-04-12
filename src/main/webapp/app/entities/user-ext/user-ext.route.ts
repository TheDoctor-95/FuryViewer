import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserExtComponent } from './user-ext.component';
import { UserExtDetailComponent } from './user-ext-detail.component';
import { UserExtPopupComponent } from './user-ext-dialog.component';
import { UserExtDeletePopupComponent } from './user-ext-delete-dialog.component';

export const userExtRoute: Routes = [
    {
        path: 'user-ext',
        component: UserExtComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.userExt.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-ext/:id',
        component: UserExtDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.userExt.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userExtPopupRoute: Routes = [
    {
        path: 'user-edit',
        component: UserExtPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.userExt.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-ext/:id/edit',
        component: UserExtPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.userExt.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-ext/:id/delete',
        component: UserExtDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.userExt.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
