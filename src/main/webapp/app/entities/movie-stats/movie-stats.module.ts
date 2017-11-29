import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import { FuryViewerAdminModule } from '../../admin/admin.module';
import {
    MovieStatsService,
    MovieStatsPopupService,
    MovieStatsComponent,
    MovieStatsDetailComponent,
    MovieStatsDialogComponent,
    MovieStatsPopupComponent,
    MovieStatsDeletePopupComponent,
    MovieStatsDeleteDialogComponent,
    movieStatsRoute,
    movieStatsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...movieStatsRoute,
    ...movieStatsPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        FuryViewerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MovieStatsComponent,
        MovieStatsDetailComponent,
        MovieStatsDialogComponent,
        MovieStatsDeleteDialogComponent,
        MovieStatsPopupComponent,
        MovieStatsDeletePopupComponent,
    ],
    entryComponents: [
        MovieStatsComponent,
        MovieStatsDialogComponent,
        MovieStatsPopupComponent,
        MovieStatsDeleteDialogComponent,
        MovieStatsDeletePopupComponent,
    ],
    providers: [
        MovieStatsService,
        MovieStatsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerMovieStatsModule {}
