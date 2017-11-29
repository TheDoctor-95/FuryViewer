import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { HatredSeries } from './hatred-series.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class HatredSeriesService {

    private resourceUrl = SERVER_API_URL + 'api/hatred-series';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(hatredSeries: HatredSeries): Observable<HatredSeries> {
        const copy = this.convert(hatredSeries);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(hatredSeries: HatredSeries): Observable<HatredSeries> {
        const copy = this.convert(hatredSeries);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<HatredSeries> {
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
     * Convert a returned JSON object to HatredSeries.
     */
    private convertItemFromServer(json: any): HatredSeries {
        const entity: HatredSeries = Object.assign(new HatredSeries(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a HatredSeries to a JSON which can be sent to the server.
     */
    private convert(hatredSeries: HatredSeries): HatredSeries {
        const copy: HatredSeries = Object.assign({}, hatredSeries);

        copy.date = this.dateUtils.toDate(hatredSeries.date);
        return copy;
    }
}
