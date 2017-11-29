import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import { FuryViewerAdminModule } from '../../admin/admin.module';
import {
    RateSeriesService,
    RateSeriesPopupService,
    RateSeriesComponent,
    RateSeriesDetailComponent,
    RateSeriesDialogComponent,
    RateSeriesPopupComponent,
    RateSeriesDeletePopupComponent,
    RateSeriesDeleteDialogComponent,
    rateSeriesRoute,
    rateSeriesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rateSeriesRoute,
    ...rateSeriesPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        FuryViewerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RateSeriesComponent,
        RateSeriesDetailComponent,
        RateSeriesDialogComponent,
        RateSeriesDeleteDialogComponent,
        RateSeriesPopupComponent,
        RateSeriesDeletePopupComponent,
    ],
    entryComponents: [
        RateSeriesComponent,
        RateSeriesDialogComponent,
        RateSeriesPopupComponent,
        RateSeriesDeleteDialogComponent,
        RateSeriesDeletePopupComponent,
    ],
    providers: [
        RateSeriesService,
        RateSeriesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerRateSeriesModule {}
