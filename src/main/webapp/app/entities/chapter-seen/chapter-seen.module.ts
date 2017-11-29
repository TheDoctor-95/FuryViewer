import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import { FuryViewerAdminModule } from '../../admin/admin.module';
import {
    ChapterSeenService,
    ChapterSeenPopupService,
    ChapterSeenComponent,
    ChapterSeenDetailComponent,
    ChapterSeenDialogComponent,
    ChapterSeenPopupComponent,
    ChapterSeenDeletePopupComponent,
    ChapterSeenDeleteDialogComponent,
    chapterSeenRoute,
    chapterSeenPopupRoute,
} from './';

const ENTITY_STATES = [
    ...chapterSeenRoute,
    ...chapterSeenPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        FuryViewerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ChapterSeenComponent,
        ChapterSeenDetailComponent,
        ChapterSeenDialogComponent,
        ChapterSeenDeleteDialogComponent,
        ChapterSeenPopupComponent,
        ChapterSeenDeletePopupComponent,
    ],
    entryComponents: [
        ChapterSeenComponent,
        ChapterSeenDialogComponent,
        ChapterSeenPopupComponent,
        ChapterSeenDeleteDialogComponent,
        ChapterSeenDeletePopupComponent,
    ],
    providers: [
        ChapterSeenService,
        ChapterSeenPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerChapterSeenModule {}
