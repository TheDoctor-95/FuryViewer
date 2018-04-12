import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MainComponent } from './main.component';
export const MainRoute: Routes = [
    {
        path: 'main',
        component: MainComponent,
        data: {
            authorities: [],
            pageTitle: 'furyViewerApp.main-main.home.title'
        }
    },
];
