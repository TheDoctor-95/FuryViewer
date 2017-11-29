import { BaseEntity, User } from './../../shared';

export class ReviewMovie implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public title?: string,
        public review?: string,
        public movie?: BaseEntity,
        public user?: User,
    ) {
    }
}
