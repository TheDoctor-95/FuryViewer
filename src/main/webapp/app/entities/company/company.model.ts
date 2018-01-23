import { BaseEntity } from './../../shared';

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public fundingDate?: any,
        public clossingDate?: any,
        public imgUrl?: string,
        public country?: BaseEntity,
        public movies?: BaseEntity[],
        public series?: BaseEntity[],
    ) {
    }
}
