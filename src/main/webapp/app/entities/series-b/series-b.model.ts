import { BaseEntity } from './../../shared';

export const enum SeriesEmittingEnum {
    'canceled',
    ' waiting_new_season',
    ' waiting_start',
    ' ended',
    ' emiting'
}

export class SeriesB implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public state?: SeriesEmittingEnum,
        public release_date?: any,
        public img_url?: string,
        public imdb_id?: string,
        public awards?: string,
        public country?: BaseEntity,
    ) {
    }
}
