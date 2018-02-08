import { BaseEntity, User } from './../../shared';

export class AchievementsAchievs implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public user?: User,
        public achievement?: BaseEntity,
    ) {
    }
}
