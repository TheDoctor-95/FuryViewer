import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import {
    EpisodeService,
    EpisodePopupService,
    EpisodeComponent,
    EpisodeDetailComponent,
    EpisodeDialogComponent,
    EpisodePopupComponent,
    EpisodeDeletePopupComponent,
    EpisodeDeleteDialogComponent,
    episodeRoute,
    episodePopupRoute,
} from './';

const ENTITY_STATES = [
    ...episodeRoute,
    ...episodePopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EpisodeComponent,
        EpisodeDetailComponent,
        EpisodeDialogComponent,
        EpisodeDeleteDialogComponent,
        EpisodePopupComponent,
        EpisodeDeletePopupComponent,
    ],
    entryComponents: [
        EpisodeComponent,
        EpisodeDialogComponent,
        EpisodePopupComponent,
        EpisodeDeleteDialogComponent,
        EpisodeDeletePopupComponent,
    ],
    providers: [
        EpisodeService,
        EpisodePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerEpisodeModule {}
