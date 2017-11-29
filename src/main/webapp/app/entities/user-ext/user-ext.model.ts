import { BaseEntity, User } from './../../shared';

export class UserExt implements BaseEntity {
    constructor(
        public id?: number,
        public imageContentType?: string,
        public image?: any,
        public locationGoogleMaps?: string,
        public latitude?: number,
        public longitude?: number,
        public user?: User,
    ) {
    }
}
