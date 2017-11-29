import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import { FuryViewerAdminModule } from '../../admin/admin.module';
import {
    HatredArtistService,
    HatredArtistPopupService,
    HatredArtistComponent,
    HatredArtistDetailComponent,
    HatredArtistDialogComponent,
    HatredArtistPopupComponent,
    HatredArtistDeletePopupComponent,
    HatredArtistDeleteDialogComponent,
    hatredArtistRoute,
    hatredArtistPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hatredArtistRoute,
    ...hatredArtistPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        FuryViewerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HatredArtistComponent,
        HatredArtistDetailComponent,
        HatredArtistDialogComponent,
        HatredArtistDeleteDialogComponent,
        HatredArtistPopupComponent,
        HatredArtistDeletePopupComponent,
    ],
    entryComponents: [
        HatredArtistComponent,
        HatredArtistDialogComponent,
        HatredArtistPopupComponent,
        HatredArtistDeleteDialogComponent,
        HatredArtistDeletePopupComponent,
    ],
    providers: [
        HatredArtistService,
        HatredArtistPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerHatredArtistModule {}
