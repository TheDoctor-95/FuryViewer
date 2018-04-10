export class EpisodeSeasonModel {
    constructor(
	public id?: number,
    public number?: string,
    public title?: string,
    public releaseDate?: any,
    public plot?: string,
    public duration?: number,
    public seen?: boolean
    ){
    }
};
