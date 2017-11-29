import { BaseEntity, User } from './../../shared';

export class HatredMovie implements BaseEntity {
    constructor(
        public id?: number,
        public hated?: boolean,
        public date?: any,
        public movie?: BaseEntity,
        public user?: User,
    ) {
        this.hated = false;
    }
}
