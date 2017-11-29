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
        public imgContentType?: string,
        public img?: any,
        public state?: SeriesEmittingEnum,
        public releaseDate?: any,
        public director?: BaseEntity,
        public scriptwriter?: BaseEntity,
        public company?: BaseEntity,
        public actors?: BaseEntity[],
        public reviews?: BaseEntity[],
        public favoriteSeries?: BaseEntity[],
        public rateSeries?: BaseEntity[],
        public stats?: BaseEntity[],
        public hatedSeries?: BaseEntity[],
        public seasons?: BaseEntity[],
        public socials?: BaseEntity[],
        public genres?: BaseEntity[],
    ) {
    }
}
