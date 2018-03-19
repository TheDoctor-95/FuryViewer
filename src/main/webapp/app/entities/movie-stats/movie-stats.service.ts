import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MovieStats } from './movie-stats.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MovieStatsService {

    private resourceUrl = SERVER_API_URL + 'api/movie-stats';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(movieStats: MovieStats): Observable<MovieStats> {
        const copy = this.convert(movieStats);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    stat(id: number, stat: string): Observable<MovieStats> {
        return this.http.post(`${this.resourceUrl}/idMovie/${id}/state/${stat}`, "").map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(movieStats: MovieStats): Observable<MovieStats> {
        const copy = this.convert(movieStats);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MovieStats> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
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

    /**
     * Convert a returned JSON object to MovieStats.
     */
    private convertItemFromServer(json: any): MovieStats {
        const entity: MovieStats = Object.assign(new MovieStats(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a MovieStats to a JSON which can be sent to the server.
     */
    private convert(movieStats: MovieStats): MovieStats {
        const copy: MovieStats = Object.assign({}, movieStats);

        copy.date = this.dateUtils.toDate(movieStats.date);
        return copy;
    }
}
