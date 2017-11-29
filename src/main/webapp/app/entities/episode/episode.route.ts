import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EpisodeComponent } from './episode.component';
import { EpisodeDetailComponent } from './episode-detail.component';
import { EpisodePopupComponent } from './episode-dialog.component';
import { EpisodeDeletePopupComponent } from './episode-delete-dialog.component';

export const episodeRoute: Routes = [
    {
        path: 'episode',
        component: EpisodeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.episode.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'episode/:id',
        component: EpisodeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.episode.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const episodePopupRoute: Routes = [
    {
        path: 'episode-new',
        component: EpisodePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.episode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'episode/:id/edit',
        component: EpisodePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.episode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'episode/:id/delete',
        component: EpisodeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.episode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
