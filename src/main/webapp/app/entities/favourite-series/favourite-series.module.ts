import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import { FuryViewerAdminModule } from '../../admin/admin.module';
import {
    FavouriteSeriesService,
    FavouriteSeriesPopupService,
    FavouriteSeriesComponent,
    FavouriteSeriesDetailComponent,
    FavouriteSeriesDialogComponent,
    FavouriteSeriesPopupComponent,
    FavouriteSeriesDeletePopupComponent,
    FavouriteSeriesDeleteDialogComponent,
    favouriteSeriesRoute,
    favouriteSeriesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...favouriteSeriesRoute,
    ...favouriteSeriesPopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        FuryViewerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FavouriteSeriesComponent,
        FavouriteSeriesDetailComponent,
        FavouriteSeriesDialogComponent,
        FavouriteSeriesDeleteDialogComponent,
        FavouriteSeriesPopupComponent,
        FavouriteSeriesDeletePopupComponent,
    ],
    entryComponents: [
        FavouriteSeriesComponent,
        FavouriteSeriesDialogComponent,
        FavouriteSeriesPopupComponent,
        FavouriteSeriesDeleteDialogComponent,
        FavouriteSeriesDeletePopupComponent,
    ],
    providers: [
        FavouriteSeriesService,
        FavouriteSeriesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerFavouriteSeriesModule {}
