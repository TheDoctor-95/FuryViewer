import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import { FuryViewerAdminModule } from '../../admin/admin.module';
import {
    HatredMovieService,
    HatredMoviePopupService,
    HatredMovieComponent,
    HatredMovieDetailComponent,
    HatredMovieDialogComponent,
    HatredMoviePopupComponent,
    HatredMovieDeletePopupComponent,
    HatredMovieDeleteDialogComponent,
    hatredMovieRoute,
    hatredMoviePopupRoute,
} from './';

const ENTITY_STATES = [
    ...hatredMovieRoute,
    ...hatredMoviePopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        FuryViewerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HatredMovieComponent,
        HatredMovieDetailComponent,
        HatredMovieDialogComponent,
        HatredMovieDeleteDialogComponent,
        HatredMoviePopupComponent,
        HatredMovieDeletePopupComponent,
    ],
    entryComponents: [
        HatredMovieComponent,
        HatredMovieDialogComponent,
        HatredMoviePopupComponent,
        HatredMovieDeleteDialogComponent,
        HatredMovieDeletePopupComponent,
    ],
    providers: [
        HatredMovieService,
        HatredMoviePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerHatredMovieModule {}
