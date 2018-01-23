package com.furyviewer.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.UserExt.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Country.class.getName() + ".artists", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Country.class.getName() + ".companies", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Genre.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Genre.class.getName() + ".movies", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Genre.class.getName() + ".series", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Series.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Series.class.getName() + ".genres", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Series.class.getName() + ".actorMains", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Series.class.getName() + ".actorSecondaries", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Series.class.getName() + ".reviews", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Series.class.getName() + ".favoriteSeries", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Series.class.getName() + ".rateSeries", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Series.class.getName() + ".stats", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Series.class.getName() + ".hatedSeries", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Series.class.getName() + ".seasons", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Series.class.getName() + ".socials", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Season.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Season.class.getName() + ".episodes", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Episode.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Episode.class.getName() + ".seens", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Movie.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Movie.class.getName() + ".genres", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Movie.class.getName() + ".actorMains", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Movie.class.getName() + ".actorSecondaries", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Movie.class.getName() + ".reviews", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Movie.class.getName() + ".favoriteMovies", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Movie.class.getName() + ".rateMovies", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Movie.class.getName() + ".stats", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Movie.class.getName() + ".hatedMovies", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Movie.class.getName() + ".socials", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Company.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Company.class.getName() + ".movies", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Company.class.getName() + ".series", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.ReviewSeries.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.ReviewMovie.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.ArtistType.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.ArtistType.class.getName() + ".artists", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Artist.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Artist.class.getName() + ".artistTypes", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Artist.class.getName() + ".favoriteArtists", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Artist.class.getName() + ".hatredArtists", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Artist.class.getName() + ".movieDirectors", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Artist.class.getName() + ".seriesDirectors", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Artist.class.getName() + ".movieScriptwriters", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Artist.class.getName() + ".seriesScriptwriters", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Artist.class.getName() + ".movieMainActors", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Artist.class.getName() + ".movieSecondaryActors", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Artist.class.getName() + ".seriesMainActors", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Artist.class.getName() + ".seriesSecondaryActors", jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.FavouriteMovie.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.FavouriteSeries.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.RateSeries.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.RateMovie.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.FavouriteArtist.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.HatredMovie.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.HatredSeries.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.HatredArtist.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.SeriesStats.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.MovieStats.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Social.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.ChapterSeen.class.getName(), jcacheConfiguration);
            cm.createCache(com.furyviewer.domain.Episode.class.getName() + ".actors", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
