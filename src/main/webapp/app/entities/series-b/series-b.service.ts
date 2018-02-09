import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SeriesB } from './series-b.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SeriesBService {

    private resourceUrl = SERVER_API_URL + 'api/series-bs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(seriesB: SeriesB): Observable<SeriesB> {
        const copy = this.convert(seriesB);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(seriesB: SeriesB): Observable<SeriesB> {
        const copy = this.convert(seriesB);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SeriesB> {
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
     * Convert a returned JSON object to SeriesB.
     */
    private convertItemFromServer(json: any): SeriesB {
        const entity: SeriesB = Object.assign(new SeriesB(), json);
        entity.release_date = this.dateUtils
            .convertLocalDateFromServer(json.release_date);
        return entity;
    }

    /**
     * Convert a SeriesB to a JSON which can be sent to the server.
     */
    private convert(seriesB: SeriesB): SeriesB {
        const copy: SeriesB = Object.assign({}, seriesB);
        copy.release_date = this.dateUtils
            .convertLocalDateToServer(seriesB.release_date);
        return copy;
    }
}
