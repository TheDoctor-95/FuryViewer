import { BaseEntity } from './../../shared';

export class Episode implements BaseEntity {
    constructor(
        public id?: number,
        public number?: number,
        public name?: string,
        public duration?: number,
        public releaseDate?: any,
        public imdbId?: string,
        public season?: BaseEntity,
        public seens?: BaseEntity[],
        public director?: BaseEntity,
        public scriptwriter?: BaseEntity,
        public actors?: BaseEntity[],
    ) {
    }
}
