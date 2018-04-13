import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import {
    IndexService,
    IndexComponent,
    IndexRoute,
} from './';

const PAGE_SET_STATES = [
    ...IndexRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        RouterModule.forRoot(PAGE_SET_STATES, { useHash: true })
    ],
    declarations: [
    IndexComponent,
],
    entryComponents: [
    IndexComponent,
],
    providers: [
    IndexService,
],
schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class FuryViewerIndexModule {}
