import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AchievementComponent } from './achievement.component';
import { AchievementDetailComponent } from './achievement-detail.component';
import { AchievementPopupComponent } from './achievement-dialog.component';
import { AchievementDeletePopupComponent } from './achievement-delete-dialog.component';

export const achievementRoute: Routes = [
    {
        path: 'achievement',
        component: AchievementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.achievement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'achievement/:id',
        component: AchievementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.achievement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const achievementPopupRoute: Routes = [
    {
        path: 'achievement-new',
        component: AchievementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.achievement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'achievement/:id/edit',
        component: AchievementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.achievement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'achievement/:id/delete',
        component: AchievementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.achievement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
