import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { WatchlistComponent } from './watchlist.component';
export const WatchlistRoute: Routes = [
    {
        path: 'watchlist',
        component: WatchlistComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.watchlist-watchlist.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
];
