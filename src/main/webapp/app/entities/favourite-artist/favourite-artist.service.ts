import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { FavouriteArtist } from './favourite-artist.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FavouriteArtistService {

    private resourceUrl = SERVER_API_URL + 'api/favourite-artists';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(favouriteArtist: FavouriteArtist): Observable<FavouriteArtist> {
        const copy = this.convert(favouriteArtist);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(favouriteArtist: FavouriteArtist): Observable<FavouriteArtist> {
        const copy = this.convert(favouriteArtist);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<FavouriteArtist> {
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
     * Convert a returned JSON object to FavouriteArtist.
     */
    private convertItemFromServer(json: any): FavouriteArtist {
        const entity: FavouriteArtist = Object.assign(new FavouriteArtist(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a FavouriteArtist to a JSON which can be sent to the server.
     */
    private convert(favouriteArtist: FavouriteArtist): FavouriteArtist {
        const copy: FavouriteArtist = Object.assign({}, favouriteArtist);

        copy.date = this.dateUtils.toDate(favouriteArtist.date);
        return copy;
    }
}
