import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CompanyComponent } from './company.component';
import { CompanyDetailComponent } from './company-detail.component';
import { CompanyPopupComponent } from './company-dialog.component';
import { CompanyDeletePopupComponent } from './company-delete-dialog.component';

export const companyRoute: Routes = [
    {
        path: 'home',
        component: CompanyComponent,
        data: {
            authorities: [],
            pageTitle: 'furyViewerApp.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company/:id',
        component: CompanyDetailComponent,
        data: {
            authorities: [],
            pageTitle: 'furyViewerApp.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyPopupRoute: Routes = [
    {
        path: 'company-new',
        component: CompanyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.company.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company/:id/edit',
        component: CompanyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.company.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company/:id/delete',
        component: CompanyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.company.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
