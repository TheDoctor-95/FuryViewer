import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ReviewMovie } from './review-movie.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReviewMovieService {

    private resourceUrl = SERVER_API_URL + 'api/review-movies';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(reviewMovie: ReviewMovie): Observable<ReviewMovie> {
        const copy = this.convert(reviewMovie);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(reviewMovie: ReviewMovie): Observable<ReviewMovie> {
        const copy = this.convert(reviewMovie);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ReviewMovie> {
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
     * Convert a returned JSON object to ReviewMovie.
     */
    private convertItemFromServer(json: any): ReviewMovie {
        const entity: ReviewMovie = Object.assign(new ReviewMovie(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a ReviewMovie to a JSON which can be sent to the server.
     */
    private convert(reviewMovie: ReviewMovie): ReviewMovie {
        const copy: ReviewMovie = Object.assign({}, reviewMovie);

        copy.date = this.dateUtils.toDate(reviewMovie.date);
        return copy;
    }
}
