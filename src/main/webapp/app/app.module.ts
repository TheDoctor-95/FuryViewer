import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { FuryViewerSharedModule, UserRouteAccessService } from './shared';
import { FuryViewerHomeModule } from './home/home.module';
import { FuryViewerAdminModule } from './admin/admin.module';
import { FuryViewerAccountModule } from './account/account.module';
import { FuryViewerPageSetsModule } from './pages/page-sets.module';
import { FuryViewerEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { SidebarModule } from 'ng-sidebar';
import { ClickOutsideModule } from 'ng4-click-outside'

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';
import {Globals} from './shared/globals';

@NgModule({
    imports: [
        BrowserModule,
        SidebarModule.forRoot(),
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        FuryViewerSharedModule,
        FuryViewerHomeModule,
        FuryViewerAdminModule,
        FuryViewerPageSetsModule,
        FuryViewerAccountModule,
        FuryViewerEntityModule,
        ClickOutsideModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService,
        Globals
    ],
    bootstrap: [ JhiMainComponent ]
})
export class FuryViewerAppModule {}
