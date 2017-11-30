import { BaseEntity, User } from './../../shared';

export const enum MovieStatsEnum {
    'PENDING',
    'SEEN'
}

export class MovieStats implements BaseEntity {
    constructor(
        public id?: number,
        public status?: MovieStatsEnum,
        public date?: any,
        public movie?: BaseEntity,
        public user?: User,
    ) {
    }
}
