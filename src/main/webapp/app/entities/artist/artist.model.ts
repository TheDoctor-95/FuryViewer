import { BaseEntity } from './../../shared';

export class Artist implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public birthdate?: any,
        public sex?: string,
        public deathdate?: any,
        public imgUrl?: string,
        public imdb_id?: string,
        public awards?: string,
        public country?: BaseEntity,
        public artistTypes?: BaseEntity[],
        public favoriteArtists?: BaseEntity[],
        public hatredArtists?: BaseEntity[],
        public movieDirectors?: BaseEntity[],
        public seriesDirectors?: BaseEntity[],
        public movieScriptwriters?: BaseEntity[],
        public seriesScriptwriters?: BaseEntity[],
        public movieMainActors?: BaseEntity[],
        public movieSecondaryActors?: BaseEntity[],
        public seriesMainActors?: BaseEntity[],
        public seriesSecondaryActors?: BaseEntity[],
    ) {
    }
}
