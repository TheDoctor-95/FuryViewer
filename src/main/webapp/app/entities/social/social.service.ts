import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Social } from './social.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SocialService {

    private resourceUrl = SERVER_API_URL + 'api/socials';

    constructor(private http: Http) { }

    create(social: Social): Observable<Social> {
        const copy = this.convert(social);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(social: Social): Observable<Social> {
        const copy = this.convert(social);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Social> {
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
     * Convert a returned JSON object to Social.
     */
    private convertItemFromServer(json: any): Social {
        const entity: Social = Object.assign(new Social(), json);
        return entity;
    }

    /**
     * Convert a Social to a JSON which can be sent to the server.
     */
    private convert(social: Social): Social {
        const copy: Social = Object.assign({}, social);
        return copy;
    }
}
