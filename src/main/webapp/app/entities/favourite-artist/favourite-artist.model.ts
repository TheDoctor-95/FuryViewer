import { BaseEntity, User } from './../../shared';

export class FavouriteArtist implements BaseEntity {
    constructor(
        public id?: number,
        public liked?: boolean,
        public date?: any,
        public artist?: BaseEntity,
        public user?: User,
    ) {
        this.liked = false;
    }
}
