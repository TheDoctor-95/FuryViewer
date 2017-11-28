import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FuryViewerUserExtModule } from './user-ext/user-ext.module';
import { FuryViewerCountryModule } from './country/country.module';
import { FuryViewerGenreModule } from './genre/genre.module';
import { FuryViewerSeriesModule } from './series/series.module';
import { FuryViewerSeasonModule } from './season/season.module';
import { FuryViewerEpisodeModule } from './episode/episode.module';
import { FuryViewerMovieModule } from './movie/movie.module';
import { FuryViewerCompanyModule } from './company/company.module';
import { FuryViewerReviewSeriesModule } from './review-series/review-series.module';
import { FuryViewerReviewMovieModule } from './review-movie/review-movie.module';
import { FuryViewerArtistTypeModule } from './artist-type/artist-type.module';
import { FuryViewerArtistModule } from './artist/artist.module';
import { FuryViewerFavouriteMovieModule } from './favourite-movie/favourite-movie.module';
import { FuryViewerFavouriteSeriesModule } from './favourite-series/favourite-series.module';
import { FuryViewerRateSeriesModule } from './rate-series/rate-series.module';
import { FuryViewerRateMovieModule } from './rate-movie/rate-movie.module';
import { FuryViewerFavouriteArtistModule } from './favourite-artist/favourite-artist.module';
import { FuryViewerHatredMovieModule } from './hatred-movie/hatred-movie.module';
import { FuryViewerHatredSeriesModule } from './hatred-series/hatred-series.module';
import { FuryViewerHatredArtistModule } from './hatred-artist/hatred-artist.module';
import { FuryViewerSeriesStatsModule } from './series-stats/series-stats.module';
import { FuryViewerMovieStatsModule } from './movie-stats/movie-stats.module';
import { FuryViewerSocialModule } from './social/social.module';
import { FuryViewerChapterSeenModule } from './chapter-seen/chapter-seen.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FuryViewerUserExtModule,
        FuryViewerCountryModule,
        FuryViewerGenreModule,
        FuryViewerSeriesModule,
        FuryViewerSeasonModule,
        FuryViewerEpisodeModule,
        FuryViewerMovieModule,
        FuryViewerCompanyModule,
        FuryViewerReviewSeriesModule,
        FuryViewerReviewMovieModule,
        FuryViewerArtistTypeModule,
        FuryViewerArtistModule,
        FuryViewerFavouriteMovieModule,
        FuryViewerFavouriteSeriesModule,
        FuryViewerRateSeriesModule,
        FuryViewerRateMovieModule,
        FuryViewerFavouriteArtistModule,
        FuryViewerHatredMovieModule,
        FuryViewerHatredSeriesModule,
        FuryViewerHatredArtistModule,
        FuryViewerSeriesStatsModule,
        FuryViewerMovieStatsModule,
        FuryViewerSocialModule,
        FuryViewerChapterSeenModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FuryViewerEntityModule {}
