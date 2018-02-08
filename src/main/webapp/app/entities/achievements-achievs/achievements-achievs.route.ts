import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AchievementsAchievsComponent } from './achievements-achievs.component';
import { AchievementsAchievsDetailComponent } from './achievements-achievs-detail.component';
import { AchievementsAchievsPopupComponent } from './achievements-achievs-dialog.component';
import { AchievementsAchievsDeletePopupComponent } from './achievements-achievs-delete-dialog.component';

export const achievementsAchievsRoute: Routes = [
    {
        path: 'achievements-achievs',
        component: AchievementsAchievsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.achievementsAchievs.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'achievements-achievs/:id',
        component: AchievementsAchievsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.achievementsAchievs.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const achievementsAchievsPopupRoute: Routes = [
    {
        path: 'achievements-achievs-new',
        component: AchievementsAchievsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.achievementsAchievs.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'achievements-achievs/:id/edit',
        component: AchievementsAchievsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.achievementsAchievs.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'achievements-achievs/:id/delete',
        component: AchievementsAchievsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'furyViewerApp.achievementsAchievs.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
