import { BaseEntity, User } from './../../shared';

export class RateMovie implements BaseEntity {
    constructor(
        public id?: number,
        public rate?: number,
        public date?: any,
        public movie?: BaseEntity,
        public user?: User,
    ) {
    }
}
