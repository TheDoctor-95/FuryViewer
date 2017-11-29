import { BaseEntity, User } from './../../shared';

export class FavouriteSeries implements BaseEntity {
    constructor(
        public id?: number,
        public liked?: boolean,
        public date?: any,
        public series?: BaseEntity,
        public user?: User,
    ) {
        this.liked = false;
    }
}
