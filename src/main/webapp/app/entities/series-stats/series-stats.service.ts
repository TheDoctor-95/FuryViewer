import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SeriesStats } from './series-stats.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SeriesStatsService {

    private resourceUrl = SERVER_API_URL + 'api/series-stats';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(seriesStats: SeriesStats): Observable<SeriesStats> {
        const copy = this.convert(seriesStats);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(seriesStats: SeriesStats): Observable<SeriesStats> {
        const copy = this.convert(seriesStats);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SeriesStats> {
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
     * Convert a returned JSON object to SeriesStats.
     */
    private convertItemFromServer(json: any): SeriesStats {
        const entity: SeriesStats = Object.assign(new SeriesStats(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a SeriesStats to a JSON which can be sent to the server.
     */
    private convert(seriesStats: SeriesStats): SeriesStats {
        const copy: SeriesStats = Object.assign({}, seriesStats);

        copy.date = this.dateUtils.toDate(seriesStats.date);
        return copy;
    }
}
