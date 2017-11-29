import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import {
    ArtistTypeService,
    ArtistTypePopupService,
    ArtistTypeComponent,
    ArtistTypeDetailComponent,
    ArtistTypeDialogComponent,
    ArtistTypePopupComponent,
    ArtistTypeDeletePopupComponent,
    ArtistTypeDeleteDialogComponent,
    artistTypeRoute,
    artistTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...artistTypeRoute,
    ...artistTypePopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ArtistTypeComponent,
        ArtistTypeDetailComponent,
        ArtistTypeDialogComponent,
        ArtistTypeDeleteDialogComponent,
        ArtistTypePopupComponent,
        ArtistTypeDeletePopupComponent,
    ],
    entryComponents: [
        ArtistTypeComponent,
        ArtistTypeDialogComponent,
        ArtistTypePopupComponent,
        ArtistTypeDeleteDialogComponent,
        ArtistTypeDeletePopupComponent,
    ],
    providers: [
        ArtistTypeService,
        ArtistTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerArtistTypeModule {}
