import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { HatredMovie } from './hatred-movie.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class HatredMovieService {

    private resourceUrl = SERVER_API_URL + 'api/hatred-movies';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(hatredMovie: HatredMovie): Observable<HatredMovie> {
        const copy = this.convert(hatredMovie);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    hate(idMovie: number): Observable<HatredMovie> {
        return this.http.post(`${this.resourceUrl}/id/${idMovie}/hate`,"").map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(hatredMovie: HatredMovie): Observable<HatredMovie> {
        const copy = this.convert(hatredMovie);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<HatredMovie> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    getIfHatred(id: number): Observable<HatredMovie>{
        return this.http.get(`${this.resourceUrl}/${id}/user`).map((res: Response) => {
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
     * Convert a returned JSON object to HatredMovie.
     */
    private convertItemFromServer(json: any): HatredMovie {
        const entity: HatredMovie = Object.assign(new HatredMovie(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a HatredMovie to a JSON which can be sent to the server.
     */
    private convert(hatredMovie: HatredMovie): HatredMovie {
        const copy: HatredMovie = Object.assign({}, hatredMovie);

        copy.date = this.dateUtils.toDate(hatredMovie.date);
        return copy;
    }
}
