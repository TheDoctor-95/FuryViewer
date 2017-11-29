import { BaseEntity } from './../../shared';

export class Genre implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public movies?: BaseEntity[],
        public series?: BaseEntity[],
    ) {
    }
}
