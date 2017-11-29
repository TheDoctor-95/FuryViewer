import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import { FuryViewerAdminModule } from '../../admin/admin.module';
import {
    SeriesStatsService,
    SeriesStatsPopupService,
    SeriesStatsComponent,
    SeriesStatsDetailComponent,
    SeriesStatsDialogComponent,
    SeriesStatsPopupComponent,
    SeriesStatsDeletePopupComponent,
    SeriesStatsDeleteDialogComponent,
    seriesStatsRoute,
    seriesStatsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...seriesStatsRoute,
    ...seriesStatsPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        FuryViewerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SeriesStatsComponent,
        SeriesStatsDetailComponent,
        SeriesStatsDialogComponent,
        SeriesStatsDeleteDialogComponent,
        SeriesStatsPopupComponent,
        SeriesStatsDeletePopupComponent,
    ],
    entryComponents: [
        SeriesStatsComponent,
        SeriesStatsDialogComponent,
        SeriesStatsPopupComponent,
        SeriesStatsDeleteDialogComponent,
        SeriesStatsDeletePopupComponent,
    ],
    providers: [
        SeriesStatsService,
        SeriesStatsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerSeriesStatsModule {}
