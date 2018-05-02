import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Series } from './series.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SeriesService {

    private resourceUrl = SERVER_API_URL + 'api/series';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(series: Series): Observable<Series> {
        const copy = this.convert(series);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(series: Series): Observable<Series> {
        const copy = this.convert(series);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Series> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    getFavHate(id: number): Observable<number> {
        return this.http.get(`${this.resourceUrl}/totalFavHate/${id}`).map((res: Response) => {
            return  res.json();
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
     * Convert a returned JSON object to Series.
     */
    private convertItemFromServer(json: any): Series {
        const entity: Series = Object.assign(new Series(), json);
        entity.releaseDate = this.dateUtils
            .convertLocalDateFromServer(json.releaseDate);
        return entity;
    }

    /**
     * Convert a Series to a JSON which can be sent to the server.
     */
    private convert(series: Series): Series {
        const copy: Series = Object.assign({}, series);
        copy.releaseDate = this.dateUtils
            .convertLocalDateToServer(series.releaseDate);
        return copy;
    }
}
