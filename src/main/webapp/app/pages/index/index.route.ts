import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { IndexComponent } from './index.component';
    export const IndexRoute: Routes = [
    {
        path: 'index',
        component: IndexComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.index.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
];

