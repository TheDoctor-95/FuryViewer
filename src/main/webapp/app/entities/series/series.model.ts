import { BaseEntity } from './../../shared';

export const enum SeriesEmittingEnum {
    'CANCELED',
    'WAITING_NEW_SEASON',
    'WAITING_START',
    'ENDED',
    'EMITTING'
}

export class Series implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public state?: SeriesEmittingEnum,
        public releaseDate?: any,
        public imgUrl?: string,
        public imdb_id?: string,
        public awards?: string,
        public director?: BaseEntity,
        public scriptwriter?: BaseEntity,
        public company?: BaseEntity,
        public genres?: BaseEntity[],
        public actorMains?: BaseEntity[],
        public actorSecondaries?: BaseEntity[],
        public reviews?: BaseEntity[],
        public favoriteSeries?: BaseEntity[],
        public rateSeries?: BaseEntity[],
        public stats?: BaseEntity[],
        public hatedSeries?: BaseEntity[],
        public seasons?: BaseEntity[],
        public socials?: BaseEntity[],
    ) {
    }
}
