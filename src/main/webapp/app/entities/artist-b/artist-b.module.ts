import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import {
    ArtistBService,
    ArtistBPopupService,
    ArtistBComponent,
    ArtistBDetailComponent,
    ArtistBDialogComponent,
    ArtistBPopupComponent,
    ArtistBDeletePopupComponent,
    ArtistBDeleteDialogComponent,
    artistBRoute,
    artistBPopupRoute,
} from './';

const ENTITY_STATES = [
    ...artistBRoute,
    ...artistBPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ArtistBComponent,
        ArtistBDetailComponent,
        ArtistBDialogComponent,
        ArtistBDeleteDialogComponent,
        ArtistBPopupComponent,
        ArtistBDeletePopupComponent,
    ],
    entryComponents: [
        ArtistBComponent,
        ArtistBDialogComponent,
        ArtistBPopupComponent,
        ArtistBDeleteDialogComponent,
        ArtistBDeletePopupComponent,
    ],
    providers: [
        ArtistBService,
        ArtistBPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerArtistBModule {}
