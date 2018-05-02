import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { RateSeries } from './rate-series.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RateSeriesService {

    private resourceUrl = SERVER_API_URL + 'api/rate-series';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(rateSeries: RateSeries): Observable<RateSeries> {
        const copy = this.convert(rateSeries);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(rateSeries: RateSeries): Observable<RateSeries> {
        const copy = this.convert(rateSeries);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<RateSeries> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    rate(id: number, mark: number): Observable<RateSeries> {
        return this.http.post(`${this.resourceUrl}/id/${id}/rate/${mark}`, '').map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    mediaSeries(id: number): Observable<number> {
        return this.http.get(`${this.resourceUrl}/mediaSeries/${id}`).map((res: Response) => {
            return  res.json();
        });
    }

    markSeriesUser(id: number): Observable<RateSeries> {
        return this.http.get(`${this.resourceUrl}/seriesRate/${id}`).map((res: Response) => {
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
     * Convert a returned JSON object to RateSeries.
     */
    private convertItemFromServer(json: any): RateSeries {
        const entity: RateSeries = Object.assign(new RateSeries(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a RateSeries to a JSON which can be sent to the server.
     */
    private convert(rateSeries: RateSeries): RateSeries {
        const copy: RateSeries = Object.assign({}, rateSeries);

        copy.date = this.dateUtils.toDate(rateSeries.date);
        return copy;
    }
}
