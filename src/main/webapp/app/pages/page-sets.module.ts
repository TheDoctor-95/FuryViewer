import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FuryViewerSearchModule } from './search/search.module';
import { FuryViewerWatchlistModule } from './watchlist/watchlist.module';
import { FuryViewerIndexModule } from './index/index.module';
/* jhipster-needle-add-pageset-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FuryViewerSearchModule,
        FuryViewerWatchlistModule,
        FuryViewerIndexModule,
        /* jhipster-needle-add-pageset-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerPageSetsModule {}
