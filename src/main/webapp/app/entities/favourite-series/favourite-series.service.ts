import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { FavouriteSeries } from './favourite-series.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FavouriteSeriesService {

    private resourceUrl = SERVER_API_URL + 'api/favourite-series';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(favouriteSeries: FavouriteSeries): Observable<FavouriteSeries> {
        const copy = this.convert(favouriteSeries);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(favouriteSeries: FavouriteSeries): Observable<FavouriteSeries> {
        const copy = this.convert(favouriteSeries);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<FavouriteSeries> {
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
     * Convert a returned JSON object to FavouriteSeries.
     */
    private convertItemFromServer(json: any): FavouriteSeries {
        const entity: FavouriteSeries = Object.assign(new FavouriteSeries(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a FavouriteSeries to a JSON which can be sent to the server.
     */
    private convert(favouriteSeries: FavouriteSeries): FavouriteSeries {
        const copy: FavouriteSeries = Object.assign({}, favouriteSeries);

        copy.date = this.dateUtils.toDate(favouriteSeries.date);
        return copy;
    }
}
