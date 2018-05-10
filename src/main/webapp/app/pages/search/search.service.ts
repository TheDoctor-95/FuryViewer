import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Search } from './search.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import {Movie} from '../../entities/movie';

@Injectable()
export class SearchService {

    private resourceUrl = SERVER_API_URL + 'api/search/search';

    constructor(private http: Http) { }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    public import(name: string): Observable<Movie> {
        return this.http.get(`api/multiImportByTitle/${name}`)
            .map((res: ResponseWrapper) => {
                return res.json;
            });
    }

    /**
     * Convert a returned JSON object to Search.
     */
    private convertItemFromServer(json: any): Search {
        const entity: Search = Object.assign(new Search(), json);
        return entity;
    }

    /**
     * Convert a Search to a JSON which can be sent to the server.
     */
    private convert(search: Search): Search {
        const copy: Search = Object.assign({}, search);
        return copy;
    }
}
