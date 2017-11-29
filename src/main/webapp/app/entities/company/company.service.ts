import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Company } from './company.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyService {

    private resourceUrl = SERVER_API_URL + 'api/companies';

    constructor(private http: Http) { }

    create(company: Company): Observable<Company> {
        const copy = this.convert(company);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(company: Company): Observable<Company> {
        const copy = this.convert(company);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Company> {
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
     * Convert a returned JSON object to Company.
     */
    private convertItemFromServer(json: any): Company {
        const entity: Company = Object.assign(new Company(), json);
        return entity;
    }

    /**
     * Convert a Company to a JSON which can be sent to the server.
     */
    private convert(company: Company): Company {
        const copy: Company = Object.assign({}, company);
        return copy;
    }
}
