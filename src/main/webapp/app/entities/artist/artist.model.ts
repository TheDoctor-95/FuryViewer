import { BaseEntity } from './../../shared';

export class Artist implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public birthdate?: any,
        public sex?: string,
        public deathdate?: any,
        public imgUrl?: string,
        public imdb_id?: string,
        public awards?: string,
        public biography?: string,
        public country?: BaseEntity,
        public artistTypes?: BaseEntity[],
        public favoriteArtists?: BaseEntity[],
        public hatredArtists?: BaseEntity[],
        public movieDirectors?: BaseEntity[],
        public movieScriptwriters?: BaseEntity[],
        public movieMainActors?: BaseEntity[],
        public movieSecondaryActors?: BaseEntity[],
    ) {
    }
}
