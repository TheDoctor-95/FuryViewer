import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SearchComponent } from './search.component';
export const SearchRoute: Routes = [
    {
        path: 'search-search',
        component: SearchComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.search-search.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
];
