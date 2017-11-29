import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import {
    SeriesService,
    SeriesPopupService,
    SeriesComponent,
    SeriesDetailComponent,
    SeriesDialogComponent,
    SeriesPopupComponent,
    SeriesDeletePopupComponent,
    SeriesDeleteDialogComponent,
    seriesRoute,
    seriesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...seriesRoute,
    ...seriesPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SeriesComponent,
        SeriesDetailComponent,
        SeriesDialogComponent,
        SeriesDeleteDialogComponent,
        SeriesPopupComponent,
        SeriesDeletePopupComponent,
    ],
    entryComponents: [
        SeriesComponent,
        SeriesDialogComponent,
        SeriesPopupComponent,
        SeriesDeleteDialogComponent,
        SeriesDeletePopupComponent,
    ],
    providers: [
        SeriesService,
        SeriesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerSeriesModule {}
