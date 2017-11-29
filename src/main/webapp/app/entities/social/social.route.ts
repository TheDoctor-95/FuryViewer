import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SocialComponent } from './social.component';
import { SocialDetailComponent } from './social-detail.component';
import { SocialPopupComponent } from './social-dialog.component';
import { SocialDeletePopupComponent } from './social-delete-dialog.component';

export const socialRoute: Routes = [
    {
        path: 'social',
        component: SocialComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.social.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'social/:id',
        component: SocialDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.social.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const socialPopupRoute: Routes = [
    {
        path: 'social-new',
        component: SocialPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.social.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'social/:id/edit',
        component: SocialPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.social.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'social/:id/delete',
        component: SocialDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.social.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
