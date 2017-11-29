import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import { FuryViewerAdminModule } from '../../admin/admin.module';
import {
    HatredSeriesService,
    HatredSeriesPopupService,
    HatredSeriesComponent,
    HatredSeriesDetailComponent,
    HatredSeriesDialogComponent,
    HatredSeriesPopupComponent,
    HatredSeriesDeletePopupComponent,
    HatredSeriesDeleteDialogComponent,
    hatredSeriesRoute,
    hatredSeriesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hatredSeriesRoute,
    ...hatredSeriesPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        FuryViewerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HatredSeriesComponent,
        HatredSeriesDetailComponent,
        HatredSeriesDialogComponent,
        HatredSeriesDeleteDialogComponent,
        HatredSeriesPopupComponent,
        HatredSeriesDeletePopupComponent,
    ],
    entryComponents: [
        HatredSeriesComponent,
        HatredSeriesDialogComponent,
        HatredSeriesPopupComponent,
        HatredSeriesDeleteDialogComponent,
        HatredSeriesDeletePopupComponent,
    ],
    providers: [
        HatredSeriesService,
        HatredSeriesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerHatredSeriesModule {}
