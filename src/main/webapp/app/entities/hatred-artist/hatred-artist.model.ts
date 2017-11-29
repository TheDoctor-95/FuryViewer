import { BaseEntity, User } from './../../shared';

export class HatredArtist implements BaseEntity {
    constructor(
        public id?: number,
        public hated?: boolean,
        public date?: any,
        public artist?: BaseEntity,
        public user?: User,
    ) {
        this.hated = false;
    }
}
