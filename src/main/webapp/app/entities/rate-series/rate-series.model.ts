import { BaseEntity, User } from './../../shared';

export class RateSeries implements BaseEntity {
    constructor(
        public id?: number,
        public rate?: number,
        public date?: any,
        public series?: BaseEntity,
        public user?: User,
    ) {
    }
}
