import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ArtistB } from './artist-b.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ArtistBService {

    private resourceUrl = SERVER_API_URL + 'api/artist-bs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(artistB: ArtistB): Observable<ArtistB> {
        const copy = this.convert(artistB);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(artistB: ArtistB): Observable<ArtistB> {
        const copy = this.convert(artistB);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ArtistB> {
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
     * Convert a returned JSON object to ArtistB.
     */
    private convertItemFromServer(json: any): ArtistB {
        const entity: ArtistB = Object.assign(new ArtistB(), json);
        entity.birthdate = this.dateUtils
            .convertLocalDateFromServer(json.birthdate);
        entity.deathdate = this.dateUtils
            .convertLocalDateFromServer(json.deathdate);
        return entity;
    }

    /**
     * Convert a ArtistB to a JSON which can be sent to the server.
     */
    private convert(artistB: ArtistB): ArtistB {
        const copy: ArtistB = Object.assign({}, artistB);
        copy.birthdate = this.dateUtils
            .convertLocalDateToServer(artistB.birthdate);
        copy.deathdate = this.dateUtils
            .convertLocalDateToServer(artistB.deathdate);
        return copy;
    }
}
