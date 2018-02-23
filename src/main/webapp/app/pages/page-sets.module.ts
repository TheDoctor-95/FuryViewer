import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FuryViewerWatchlistModule } from './watchlist/watchlist.module';
import { FuryViewerSearchModule } from './search/search.module';
/* jhipster-needle-add-pageset-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FuryViewerWatchlistModule,
        FuryViewerSearchModule,
        /* jhipster-needle-add-pageset-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerPageSetsModule {}
