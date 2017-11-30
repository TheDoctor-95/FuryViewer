import { BaseEntity, User } from './../../shared';

export class FavouriteMovie implements BaseEntity {
    constructor(
        public id?: number,
        public liked?: boolean,
        public date?: any,
        public movie?: BaseEntity,
        public user?: User,
    ) {
        this.liked = false;
    }
}
