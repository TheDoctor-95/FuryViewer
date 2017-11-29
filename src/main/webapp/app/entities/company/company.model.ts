import { BaseEntity } from './../../shared';

export class Company implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public imgContentType?: string,
        public img?: any,
        public country?: BaseEntity,
        public movies?: BaseEntity[],
        public series?: BaseEntity[],
    ) {
    }
}
