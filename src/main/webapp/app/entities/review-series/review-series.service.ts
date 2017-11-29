import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ReviewSeries } from './review-series.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReviewSeriesService {

    private resourceUrl = SERVER_API_URL + 'api/review-series';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(reviewSeries: ReviewSeries): Observable<ReviewSeries> {
        const copy = this.convert(reviewSeries);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(reviewSeries: ReviewSeries): Observable<ReviewSeries> {
        const copy = this.convert(reviewSeries);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ReviewSeries> {
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
     * Convert a returned JSON object to ReviewSeries.
     */
    private convertItemFromServer(json: any): ReviewSeries {
        const entity: ReviewSeries = Object.assign(new ReviewSeries(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a ReviewSeries to a JSON which can be sent to the server.
     */
    private convert(reviewSeries: ReviewSeries): ReviewSeries {
        const copy: ReviewSeries = Object.assign({}, reviewSeries);

        copy.date = this.dateUtils.toDate(reviewSeries.date);
        return copy;
    }
}
