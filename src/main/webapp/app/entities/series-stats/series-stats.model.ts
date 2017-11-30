import { BaseEntity, User } from './../../shared';

export const enum SeriesStatsEnum {
    'PENDING',
    'FOLLOWING',
    'SEEN'
}

export class SeriesStats implements BaseEntity {
    constructor(
        public id?: number,
        public status?: SeriesStatsEnum,
        public date?: any,
        public serie?: BaseEntity,
        public user?: User,
    ) {
    }
}
