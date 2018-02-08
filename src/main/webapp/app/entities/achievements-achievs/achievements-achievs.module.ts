import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import { FuryViewerAdminModule } from '../../admin/admin.module';
import {
    AchievementsAchievsService,
    AchievementsAchievsPopupService,
    AchievementsAchievsComponent,
    AchievementsAchievsDetailComponent,
    AchievementsAchievsDialogComponent,
    AchievementsAchievsPopupComponent,
    AchievementsAchievsDeletePopupComponent,
    AchievementsAchievsDeleteDialogComponent,
    achievementsAchievsRoute,
    achievementsAchievsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...achievementsAchievsRoute,
    ...achievementsAchievsPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        FuryViewerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AchievementsAchievsComponent,
        AchievementsAchievsDetailComponent,
        AchievementsAchievsDialogComponent,
        AchievementsAchievsDeleteDialogComponent,
        AchievementsAchievsPopupComponent,
        AchievementsAchievsDeletePopupComponent,
    ],
    entryComponents: [
        AchievementsAchievsComponent,
        AchievementsAchievsDialogComponent,
        AchievementsAchievsPopupComponent,
        AchievementsAchievsDeleteDialogComponent,
        AchievementsAchievsDeletePopupComponent,
    ],
    providers: [
        AchievementsAchievsService,
        AchievementsAchievsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerAchievementsAchievsModule {}
