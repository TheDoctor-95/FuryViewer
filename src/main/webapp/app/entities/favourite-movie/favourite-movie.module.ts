import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuryViewerSharedModule } from '../../shared';
import { FuryViewerAdminModule } from '../../admin/admin.module';
import {
    FavouriteMovieService,
    FavouriteMoviePopupService,
    FavouriteMovieComponent,
    FavouriteMovieDetailComponent,
    FavouriteMovieDialogComponent,
    FavouriteMoviePopupComponent,
    FavouriteMovieDeletePopupComponent,
    FavouriteMovieDeleteDialogComponent,
    favouriteMovieRoute,
    favouriteMoviePopupRoute,
} from './';

const ENTITY_STATES = [
    ...favouriteMovieRoute,
    ...favouriteMoviePopupRoute,
];

@NgModule({
    imports: [
        FuryViewerSharedModule,
        FuryViewerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FavouriteMovieComponent,
        FavouriteMovieDetailComponent,
        FavouriteMovieDialogComponent,
        FavouriteMovieDeleteDialogComponent,
        FavouriteMoviePopupComponent,
        FavouriteMovieDeletePopupComponent,
    ],
    entryComponents: [
        FavouriteMovieComponent,
        FavouriteMovieDialogComponent,
        FavouriteMoviePopupComponent,
        FavouriteMovieDeleteDialogComponent,
        FavouriteMovieDeletePopupComponent,
    ],
    providers: [
        FavouriteMovieService,
        FavouriteMoviePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerFavouriteMovieModule {}
