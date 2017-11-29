import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ArtistType } from './artist-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ArtistTypeService {

    private resourceUrl = SERVER_API_URL + 'api/artist-types';

    constructor(private http: Http) { }

    create(artistType: ArtistType): Observable<ArtistType> {
        const copy = this.convert(artistType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(artistType: ArtistType): Observable<ArtistType> {
        const copy = this.convert(artistType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ArtistType> {
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
     * Convert a returned JSON object to ArtistType.
     */
    private convertItemFromServer(json: any): ArtistType {
        const entity: ArtistType = Object.assign(new ArtistType(), json);
        return entity;
    }

    /**
     * Convert a ArtistType to a JSON which can be sent to the server.
     */
    private convert(artistType: ArtistType): ArtistType {
        const copy: ArtistType = Object.assign({}, artistType);
        return copy;
    }
}
