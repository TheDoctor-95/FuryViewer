import { BaseEntity, User } from './../../shared';

export class ReviewSeries implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public title?: string,
        public review?: string,
        public series?: BaseEntity,
        public user?: User,
    ) {
    }
}
