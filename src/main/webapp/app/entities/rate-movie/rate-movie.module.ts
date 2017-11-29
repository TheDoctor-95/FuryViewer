import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import { FuryViewerAdminModule } from '../../admin/admin.module';
import {
    RateMovieService,
    RateMoviePopupService,
    RateMovieComponent,
    RateMovieDetailComponent,
    RateMovieDialogComponent,
    RateMoviePopupComponent,
    RateMovieDeletePopupComponent,
    RateMovieDeleteDialogComponent,
    rateMovieRoute,
    rateMoviePopupRoute,
} from './';

const ENTITY_STATES = [
    ...rateMovieRoute,
    ...rateMoviePopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        FuryViewerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RateMovieComponent,
        RateMovieDetailComponent,
        RateMovieDialogComponent,
        RateMovieDeleteDialogComponent,
        RateMoviePopupComponent,
        RateMovieDeletePopupComponent,
    ],
    entryComponents: [
        RateMovieComponent,
        RateMovieDialogComponent,
        RateMoviePopupComponent,
        RateMovieDeleteDialogComponent,
        RateMovieDeletePopupComponent,
    ],
    providers: [
        RateMovieService,
        RateMoviePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerRateMovieModule {}
