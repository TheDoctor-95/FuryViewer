import { BaseEntity } from './../../shared';

export class Season implements BaseEntity {
    constructor(
        public id?: number,
        public number?: number,
        public imgContentType?: string,
        public img?: any,
        public releaseDate?: any,
        public series?: BaseEntity,
        public episodes?: BaseEntity[],
    ) {
    }
}
