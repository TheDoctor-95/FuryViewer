import { BaseEntity } from './../../shared';

export class Artist implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public birthdate?: any,
        public sex?: string,
        public alive?: boolean,
        public deathdate?: any,
        public imgContentType?: string,
        public img?: any,
        public country?: BaseEntity,
        public artistTypes?: BaseEntity[],
        public favoriteArtists?: BaseEntity[],
        public hatredArtists?: BaseEntity[],
        public movieDirectors?: BaseEntity[],
        public seriesDirectors?: BaseEntity[],
        public movieScriptwriters?: BaseEntity[],
        public seriesScriptwriters?: BaseEntity[],
        public movieActors?: BaseEntity[],
        public seriesActors?: BaseEntity[],
    ) {
        this.alive = false;
    }
}
