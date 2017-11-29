import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import {
    ArtistService,
    ArtistPopupService,
    ArtistComponent,
    ArtistDetailComponent,
    ArtistDialogComponent,
    ArtistPopupComponent,
    ArtistDeletePopupComponent,
    ArtistDeleteDialogComponent,
    artistRoute,
    artistPopupRoute,
} from './';

const ENTITY_STATES = [
    ...artistRoute,
    ...artistPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ArtistComponent,
        ArtistDetailComponent,
        ArtistDialogComponent,
        ArtistDeleteDialogComponent,
        ArtistPopupComponent,
        ArtistDeletePopupComponent,
    ],
    entryComponents: [
        ArtistComponent,
        ArtistDialogComponent,
        ArtistPopupComponent,
        ArtistDeleteDialogComponent,
        ArtistDeletePopupComponent,
    ],
    providers: [
        ArtistService,
        ArtistPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerArtistModule {}
