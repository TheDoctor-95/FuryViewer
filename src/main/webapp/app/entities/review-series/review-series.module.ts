import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import { FuryViewerAdminModule } from '../../admin/admin.module';
import {
    ReviewSeriesService,
    ReviewSeriesPopupService,
    ReviewSeriesComponent,
    ReviewSeriesDetailComponent,
    ReviewSeriesDialogComponent,
    ReviewSeriesPopupComponent,
    ReviewSeriesDeletePopupComponent,
    ReviewSeriesDeleteDialogComponent,
    reviewSeriesRoute,
    reviewSeriesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...reviewSeriesRoute,
    ...reviewSeriesPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        FuryViewerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReviewSeriesComponent,
        ReviewSeriesDetailComponent,
        ReviewSeriesDialogComponent,
        ReviewSeriesDeleteDialogComponent,
        ReviewSeriesPopupComponent,
        ReviewSeriesDeletePopupComponent,
    ],
    entryComponents: [
        ReviewSeriesComponent,
        ReviewSeriesDialogComponent,
        ReviewSeriesPopupComponent,
        ReviewSeriesDeleteDialogComponent,
        ReviewSeriesDeletePopupComponent,
    ],
    providers: [
        ReviewSeriesService,
        ReviewSeriesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerReviewSeriesModule {}
