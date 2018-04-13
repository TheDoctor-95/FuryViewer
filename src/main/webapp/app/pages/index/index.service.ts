import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Index } from './index.model';
import { createRequestOption } from '../../shared';

export type IndexResponseType = HttpResponse<Index>;
export type IndexArrayResponseType = HttpResponse<Index[]>;

@Injectable()
export class IndexService {

    private resourceUrl = SERVER_API_URL + 'api/index/index';

    constructor(private http: HttpClient) { }

    create(index: Index): Observable<IndexResponseType> {
        const copy = this.convert(index);
        return this.http.post<Index>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: IndexResponseType) => this.convertResponse(res));
    }

    update(index: Index): Observable<IndexResponseType> {
        const copy = this.convert(index);
        return this.http.put<Index>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: IndexResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<IndexResponseType> {
        const options = createRequestOption(req);
        return this.http.get<Index>(this.resourceUrl, { observe: 'response' })
            .map((res: IndexResponseType) => this.convertResponse(res));
    }

    private convertResponse(res: IndexResponseType): IndexResponseType {
        const body: Index = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: IndexArrayResponseType): IndexArrayResponseType {
        const jsonResponse: Index[] = res.body;
        const body: Index[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Index.
     */
    private convertItemFromServer(json: any): Index {
        const copy: Index = Object.assign(new Index(), json);
        return copy;
    }

    /**
     * Convert a Index to a JSON which can be sent to the server.
     */
    private convert(index: Index): Index {
        const copy: Index = Object.assign({}, index);
        return copy;
    }
}
