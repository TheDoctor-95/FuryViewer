import { BaseEntity } from './../../shared';

export class Episode implements BaseEntity {
    constructor(
        public id?: number,
        public number?: number,
        public name?: string,
        public duration?: number,
        public releaseDate?: any,
        public season?: BaseEntity,
        public seens?: BaseEntity[],
    ) {
    }
}
