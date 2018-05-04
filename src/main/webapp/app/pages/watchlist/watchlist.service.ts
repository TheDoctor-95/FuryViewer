import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Watchlist } from './watchlist.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import {FilmographyArtistModel} from "../../shared/model/filmographyArtist.model";
2
@Injectable()
export class WatchlistService {

    private resourceUrl = SERVER_API_URL + 'api/watchlist';

    constructor(private http: Http) { }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    load(multimedia: string, option: string): Observable<ResponseWrapper>{
        return this.http.get(`${this.resourceUrl}/${multimedia}/option/${option}`).map(
            (res: Response) => {
                return this.convertFilmographyResponse(res);
            }
        );
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    private convertFilmographyResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertFilmographyFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }


    /**
     * Convert a returned JSON object to Watchlist.
     */
    private convertItemFromServer(json: any): Watchlist {
        const entity: Watchlist = Object.assign(new Watchlist(), json);
        return entity;
    }

    private convertFilmographyFromServer(json: any): FilmographyArtistModel {
        const entity: FilmographyArtistModel = Object.assign(new FilmographyArtistModel(), json);
        return entity;
    }

    /**
     * Convert a Watchlist to a JSON which can be sent to the server.
     */
    private convert(watchlist: Watchlist): Watchlist {
        const copy: Watchlist = Object.assign({}, watchlist);
        return copy;
    }


}
