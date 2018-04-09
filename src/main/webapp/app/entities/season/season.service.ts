import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Season } from './season.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SeasonService {

    private resourceUrl = SERVER_API_URL + 'api/seasons';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(season: Season): Observable<Season> {
        const copy = this.convert(season);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(season: Season): Observable<Season> {
        const copy = this.convert(season);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Season> {
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

    findSeasons(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/Seasons-by-Series/${id}`)
            .map((res:Response) => this.convertResponseId(res));
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

    private convertResponseId(res: Response): ResponseWrapper{
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Season.
     */
    private convertItemFromServer(json: any): Season {
        const entity: Season = Object.assign(new Season(), json);
        entity.releaseDate = this.dateUtils
            .convertLocalDateFromServer(json.releaseDate);
        return entity;
    }

    /**
     * Convert a Season to a JSON which can be sent to the server.
     */
    private convert(season: Season): Season {
        const copy: Season = Object.assign({}, season);
        copy.releaseDate = this.dateUtils
            .convertLocalDateToServer(season.releaseDate);
        return copy;
    }
}
