import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import {
    MainService,
    MainComponent,
    MainRoute,
} from './';

const PAGE_SET_STATES = [
    ...MainRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        RouterModule.forRoot(PAGE_SET_STATES, { useHash: true })
    ],
    declarations: [
    MainComponent,
],
    entryComponents: [
    MainComponent,
],
    providers: [
    MainService,
],
schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class FuryViewerMainModule {}
