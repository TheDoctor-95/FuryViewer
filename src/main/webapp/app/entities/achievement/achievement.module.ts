import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import {
    AchievementService,
    AchievementPopupService,
    AchievementComponent,
    AchievementDetailComponent,
    AchievementDialogComponent,
    AchievementPopupComponent,
    AchievementDeletePopupComponent,
    AchievementDeleteDialogComponent,
    achievementRoute,
    achievementPopupRoute,
} from './';

const ENTITY_STATES = [
    ...achievementRoute,
    ...achievementPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AchievementComponent,
        AchievementDetailComponent,
        AchievementDialogComponent,
        AchievementDeleteDialogComponent,
        AchievementPopupComponent,
        AchievementDeletePopupComponent,
    ],
    entryComponents: [
        AchievementComponent,
        AchievementDialogComponent,
        AchievementPopupComponent,
        AchievementDeleteDialogComponent,
        AchievementDeletePopupComponent,
    ],
    providers: [
        AchievementService,
        AchievementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerAchievementModule {}
