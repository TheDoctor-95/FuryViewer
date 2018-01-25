import { BaseEntity } from './../../shared';

export class Movie implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public releaseDate?: any,
        public description?: string,
        public duration?: number,
        public imdbIdExternalApi?: string,
        public imgUrl?: string,
        public dvd_release?: string,
        public awards?: string,
        public director?: BaseEntity,
        public scriptwriter?: BaseEntity,
        public company?: BaseEntity,
        public genres?: BaseEntity[],
        public actorMains?: BaseEntity[],
        public actorSecondaries?: BaseEntity[],
        public reviews?: BaseEntity[],
        public favoriteMovies?: BaseEntity[],
        public rateMovies?: BaseEntity[],
        public stats?: BaseEntity[],
        public hatedMovies?: BaseEntity[],
        public socials?: BaseEntity[],
        public country?: BaseEntity,
    ) {
    }
}
