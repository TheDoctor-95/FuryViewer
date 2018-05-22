import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { HatredArtist } from './hatred-artist.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import {HatredSeries} from '../hatred-series/hatred-series.model';
import {BooleanModel} from "../../shared/model/boolean.model";

@Injectable()
export class HatredArtistService {

    private resourceUrl = SERVER_API_URL + 'api/hatred-artists';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(hatredArtist: HatredArtist): Observable<HatredArtist> {
        const copy = this.convert(hatredArtist);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(hatredArtist: HatredArtist): Observable<HatredArtist> {
        const copy = this.convert(hatredArtist);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<HatredArtist> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    getHate(id: number): Observable<BooleanModel> {
        return this.http.get(`${this.resourceUrl}/user/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    hatred(id: number): Observable<HatredArtist> {
        return this.http.post(`${this.resourceUrl}/Artist/${id}`, '').map((res: Response) => {
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
     * Convert a returned JSON object to HatredArtist.
     */
    private convertItemFromServer(json: any): HatredArtist {
        const entity: HatredArtist = Object.assign(new HatredArtist(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a HatredArtist to a JSON which can be sent to the server.
     */
    private convert(hatredArtist: HatredArtist): HatredArtist {
        const copy: HatredArtist = Object.assign({}, hatredArtist);

        copy.date = this.dateUtils.toDate(hatredArtist.date);
        return copy;
    }
}
