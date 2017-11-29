import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Genre } from './genre.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class GenreService {

    private resourceUrl = SERVER_API_URL + 'api/genres';

    constructor(private http: Http) { }

    create(genre: Genre): Observable<Genre> {
        const copy = this.convert(genre);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(genre: Genre): Observable<Genre> {
        const copy = this.convert(genre);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Genre> {
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
     * Convert a returned JSON object to Genre.
     */
    private convertItemFromServer(json: any): Genre {
        const entity: Genre = Object.assign(new Genre(), json);
        return entity;
    }

    /**
     * Convert a Genre to a JSON which can be sent to the server.
     */
    private convert(genre: Genre): Genre {
        const copy: Genre = Object.assign({}, genre);
        return copy;
    }
}
