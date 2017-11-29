import { BaseEntity, User } from './../../shared';

export class ChapterSeen implements BaseEntity {
    constructor(
        public id?: number,
        public seen?: boolean,
        public date?: any,
        public episode?: BaseEntity,
        public user?: User,
    ) {
        this.seen = false;
    }
}
