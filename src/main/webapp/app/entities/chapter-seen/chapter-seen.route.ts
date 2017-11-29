import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ChapterSeenComponent } from './chapter-seen.component';
import { ChapterSeenDetailComponent } from './chapter-seen-detail.component';
import { ChapterSeenPopupComponent } from './chapter-seen-dialog.component';
import { ChapterSeenDeletePopupComponent } from './chapter-seen-delete-dialog.component';

export const chapterSeenRoute: Routes = [
    {
        path: 'chapter-seen',
        component: ChapterSeenComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.chapterSeen.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'chapter-seen/:id',
        component: ChapterSeenDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.chapterSeen.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chapterSeenPopupRoute: Routes = [
    {
        path: 'chapter-seen-new',
        component: ChapterSeenPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.chapterSeen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chapter-seen/:id/edit',
        component: ChapterSeenPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.chapterSeen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chapter-seen/:id/delete',
        component: ChapterSeenDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.chapterSeen.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
