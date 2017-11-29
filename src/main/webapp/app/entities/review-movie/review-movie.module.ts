import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import { FuryViewerAdminModule } from '../../admin/admin.module';
import {
    ReviewMovieService,
    ReviewMoviePopupService,
    ReviewMovieComponent,
    ReviewMovieDetailComponent,
    ReviewMovieDialogComponent,
    ReviewMoviePopupComponent,
    ReviewMovieDeletePopupComponent,
    ReviewMovieDeleteDialogComponent,
    reviewMovieRoute,
    reviewMoviePopupRoute,
} from './';

const ENTITY_STATES = [
    ...reviewMovieRoute,
    ...reviewMoviePopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        FuryViewerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReviewMovieComponent,
        ReviewMovieDetailComponent,
        ReviewMovieDialogComponent,
        ReviewMovieDeleteDialogComponent,
        ReviewMoviePopupComponent,
        ReviewMovieDeletePopupComponent,
    ],
    entryComponents: [
        ReviewMovieComponent,
        ReviewMovieDialogComponent,
        ReviewMoviePopupComponent,
        ReviewMovieDeleteDialogComponent,
        ReviewMovieDeletePopupComponent,
    ],
    providers: [
        ReviewMovieService,
        ReviewMoviePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerReviewMovieModule {}
