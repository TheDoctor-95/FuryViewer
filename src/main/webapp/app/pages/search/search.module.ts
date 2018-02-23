import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import {
    SearchService,
    SearchComponent,
    SearchRoute,
} from './';

const PAGE_SET_STATES = [
    ...SearchRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        RouterModule.forRoot(PAGE_SET_STATES, { useHash: true })
    ],
    declarations: [
    SearchComponent,
],
    entryComponents: [
    SearchComponent,
],
    providers: [
    SearchService,
],
schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class FuryViewerSearchModule {}
