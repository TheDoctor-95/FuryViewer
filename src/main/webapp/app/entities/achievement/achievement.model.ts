import { BaseEntity } from './../../shared';

export const enum AchievementType {
    'like',
    ' hate',
    ' follow',
    ' finish'
}

export const enum EntityType {
    'series',
    ' movie'
}

export class Achievement implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public amount?: number,
        public achievementType?: AchievementType,
        public entity?: EntityType,
        public description?: string,
        public imgContentType?: string,
        public img?: any,
    ) {
    }
}
