import {EpisodeNextSeen} from './EpisodeNextSeen.model';

export class CalendarModel {
    constructor(
        public releaseDate?: any,
        public episodes?: EpisodeNextSeen[]
    ) {

    }
};
