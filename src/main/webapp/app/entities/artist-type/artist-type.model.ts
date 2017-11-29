import { BaseEntity } from './../../shared';

export const enum ArtistTypeEnum {
    'MAIN_ACTOR',
    'SECONDARY_ACTOR',
    'DIRECTOR',
    'SCRIPTWRITER'
}

export class ArtistType implements BaseEntity {
    constructor(
        public id?: number,
        public name?: ArtistTypeEnum,
        public artists?: BaseEntity[],
    ) {
    }
}
