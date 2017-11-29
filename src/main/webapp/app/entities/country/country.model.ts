import { BaseEntity } from './../../shared';

export class Country implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public urlGoogleMaps?: string,
        public latitude?: number,
        public longitude?: number,
        public artists?: BaseEntity[],
        public companies?: BaseEntity[],
    ) {
    }
}
