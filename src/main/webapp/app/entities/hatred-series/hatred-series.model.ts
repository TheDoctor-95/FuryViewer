import { BaseEntity, User } from './../../shared';

export class HatredSeries implements BaseEntity {
    constructor(
        public id?: number,
        public hated?: boolean,
        public date?: any,
        public series?: BaseEntity,
        public user?: User,
    ) {
        this.hated = false;
    }
}
