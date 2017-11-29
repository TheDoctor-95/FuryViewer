import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import {
    SocialService,
    SocialPopupService,
    SocialComponent,
    SocialDetailComponent,
    SocialDialogComponent,
    SocialPopupComponent,
    SocialDeletePopupComponent,
    SocialDeleteDialogComponent,
    socialRoute,
    socialPopupRoute,
} from './';

const ENTITY_STATES = [
    ...socialRoute,
    ...socialPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SocialComponent,
        SocialDetailComponent,
        SocialDialogComponent,
        SocialDeleteDialogComponent,
        SocialPopupComponent,
        SocialDeletePopupComponent,
    ],
    entryComponents: [
        SocialComponent,
        SocialDialogComponent,
        SocialPopupComponent,
        SocialDeleteDialogComponent,
        SocialDeletePopupComponent,
    ],
    providers: [
        SocialService,
        SocialPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerSocialModule {}
