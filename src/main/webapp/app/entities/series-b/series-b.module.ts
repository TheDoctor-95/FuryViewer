import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import {
    SeriesBService,
    SeriesBPopupService,
    SeriesBComponent,
    SeriesBDetailComponent,
    SeriesBDialogComponent,
    SeriesBPopupComponent,
    SeriesBDeletePopupComponent,
    SeriesBDeleteDialogComponent,
    seriesBRoute,
    seriesBPopupRoute,
} from './';

const ENTITY_STATES = [
    ...seriesBRoute,
    ...seriesBPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SeriesBComponent,
        SeriesBDetailComponent,
        SeriesBDialogComponent,
        SeriesBDeleteDialogComponent,
        SeriesBPopupComponent,
        SeriesBDeletePopupComponent,
    ],
    entryComponents: [
        SeriesBComponent,
        SeriesBDialogComponent,
        SeriesBPopupComponent,
        SeriesBDeleteDialogComponent,
        SeriesBDeletePopupComponent,
    ],
    providers: [
        SeriesBService,
        SeriesBPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerSeriesBModule {}
