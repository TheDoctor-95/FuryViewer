import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import {
    WatchlistService,
    WatchlistComponent,
    WatchlistRoute,
} from './';

const PAGE_SET_STATES = [
    ...WatchlistRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        RouterModule.forRoot(PAGE_SET_STATES, { useHash: true })
    ],
    declarations: [
    WatchlistComponent,
],
    entryComponents: [
    WatchlistComponent,
],
    providers: [
    WatchlistService,
],
schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class FuryViewerWatchlistModule {}
