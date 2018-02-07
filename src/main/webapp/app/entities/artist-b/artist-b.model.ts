import { BaseEntity } from './../../shared';

export class ArtistB implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public birthdate?: any,
        public sex?: string,
        public deathdate?: any,
        public imgUrl?: string,
        public imdbId?: string,
        public awards?: string,
        public country?: BaseEntity,
    ) {
    }
}
