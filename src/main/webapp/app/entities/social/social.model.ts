import { BaseEntity } from './../../shared';

export class Social implements BaseEntity {
    constructor(
        public id?: number,
        public url?: string,
        public series?: BaseEntity,
        public movie?: BaseEntity,
    ) {
    }
}
