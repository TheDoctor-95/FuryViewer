import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { RateMovie } from './rate-movie.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RateMovieService {

    private resourceUrl = SERVER_API_URL + 'api/rate-movies';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(rateMovie: RateMovie): Observable<RateMovie> {
        const copy = this.convert(rateMovie);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(rateMovie: RateMovie): Observable<RateMovie> {
        const copy = this.convert(rateMovie);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<RateMovie> {
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
     * Convert a returned JSON object to RateMovie.
     */
    private convertItemFromServer(json: any): RateMovie {
        const entity: RateMovie = Object.assign(new RateMovie(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a RateMovie to a JSON which can be sent to the server.
     */
    private convert(rateMovie: RateMovie): RateMovie {
        const copy: RateMovie = Object.assign({}, rateMovie);

        copy.date = this.dateUtils.toDate(rateMovie.date);
        return copy;
    }
}
