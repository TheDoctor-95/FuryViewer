import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Episode } from './episode.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import {EpisodeNextSeen} from '../../shared/model/EpisodeNextSeen.model';
import {EpisodeSeasonModel} from '../../shared/model/EpisodeSeason.model';
import {Movie} from "../movie/movie.model";

@Injectable()
export class EpisodeService {

    private resourceUrl = SERVER_API_URL + 'api/episodes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(episode: Episode): Observable<Episode> {
        const copy = this.convert(episode);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(episode: Episode): Observable<Episode> {
        const copy = this.convert(episode);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Episode> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    calendar(): Observable<Map<string, Movie[]>> {
        return this.http.get(`${this.resourceUrl}/calendar`)
            .map((res: Response) => {
                return res.json();
            });
    }

    nextEpisodes(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/next`)
            .map((res: Response) => this.convertNextEpisodeResponse(res));
    }

    nextEpisodes5(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/next/5`)
            .map((res: Response) => this.convertNextEpisodeResponse(res));
    }

    findEpisodeBySeasonId(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/all-episodes-from-season/${id}`)
            .map((res: Response) => this.convertSeasonEpisodeResponse(res));
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }


    private convertNextEpisodeResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertNextEpisodeFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    private convertSeasonEpisodeResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertSeasonEpisodeFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Episode.
     */
    private convertItemFromServer(json: any): Episode {
        const entity: Episode = Object.assign(new Episode(), json);
        entity.releaseDate = this.dateUtils
            .convertLocalDateFromServer(json.releaseDate);
        return entity;
    }

    private convertNextEpisodeFromServer(json: any): EpisodeNextSeen {

        const entity: EpisodeNextSeen = Object.assign(new EpisodeNextSeen(), json);
        return entity;
    }

    private convertSeasonEpisodeFromServer(json: any): EpisodeSeasonModel {
        const entity: EpisodeSeasonModel = Object.assign(new EpisodeSeasonModel(), json);
        entity.releaseDate = this.dateUtils
            .convertDateTimeFromServer(json.releaseDate);
        return entity;
    }

    /**
     * Convert a Episode to a JSON which can be sent to the server.
     */
    private convert(episode: Episode): Episode {
        const copy: Episode = Object.assign({}, episode);
        copy.releaseDate = this.dateUtils
            .convertLocalDateToServer(episode.releaseDate);
        return copy;
    }
}
