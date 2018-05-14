import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Country } from './country.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CountryService {

    private resourceUrl = SERVER_API_URL + 'api/countries';

    constructor(private http: Http) { }

    create(country: Country): Observable<Country> {
        const copy = this.convert(country);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(country: Country): Observable<Country> {
        const copy = this.convert(country);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Country> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    getAbsoluteTotalFav(): Observable<number> {
        return this.http.get(`${this.resourceUrl}/count-absolute-favourite/`).map((res: Response) => {
            return  res.json();
        });
    }

    getAbsoluteTotalHatred(): Observable<number> {
        return this.http.get(`${this.resourceUrl}/count-absolute-hatred/`).map((res: Response) => {
            return  res.json();
        });
    }

    getAbsoluteTotalArtist(): Observable<number> {
        return this.http.get(`${this.resourceUrl}/count-absolute-artist/`).map((res: Response) => {
            return  res.json();
        });
    }

    getAbsoluteTotalMovie(): Observable<number> {
        return this.http.get(`${this.resourceUrl}/count-absolute-movie/`).map((res: Response) => {
            return  res.json();
        });
    }

    getAbsoluteTotalSeries(): Observable<number> {
        return this.http.get(`${this.resourceUrl}/count-absolute-series/`).map((res: Response) => {
            return  res.json();
        });
    }

    getAbsoluteTotalUser(): Observable<number> {
        return this.http.get(`${this.resourceUrl}/count-absolute-user/`).map((res: Response) => {
            return  res.json();
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
     * Convert a returned JSON object to Country.
     */
    private convertItemFromServer(json: any): Country {
        const entity: Country = Object.assign(new Country(), json);
        return entity;
    }

    /**
     * Convert a Country to a JSON which can be sent to the server.
     */
    private convert(country: Country): Country {
        const copy: Country = Object.assign({}, country);
        return copy;
    }
}
