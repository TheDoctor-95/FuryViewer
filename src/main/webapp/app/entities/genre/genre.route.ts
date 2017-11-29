import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { GenreComponent } from './genre.component';
import { GenreDetailComponent } from './genre-detail.component';
import { GenrePopupComponent } from './genre-dialog.component';
import { GenreDeletePopupComponent } from './genre-delete-dialog.component';

export const genreRoute: Routes = [
    {
        path: 'genre',
        component: GenreComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.genre.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'genre/:id',
        component: GenreDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.genre.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const genrePopupRoute: Routes = [
    {
        path: 'genre-new',
        component: GenrePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.genre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'genre/:id/edit',
        component: GenrePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.genre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'genre/:id/delete',
        component: GenreDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.genre.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
