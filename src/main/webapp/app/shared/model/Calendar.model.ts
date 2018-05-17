import {Episode} from '../../entities/episode/episode.model';

export class CalendarModel {
    constructor(
        public releaseDate?: any,
        public episodes?: Episode[]
    ) {

    }
};
