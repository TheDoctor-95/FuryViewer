import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { FavouriteMovie } from './favourite-movie.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FavouriteMovieService {

    private resourceUrl = SERVER_API_URL + 'api/favourite-movies';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(favouriteMovie: FavouriteMovie): Observable<FavouriteMovie> {
        const copy = this.convert(favouriteMovie);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    favorite(idMovie: number, like: boolean): Observable<FavouriteMovie> {
        return this.http.post(`${this.resourceUrl}/id/${idMovie}/liked`,"").map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }


    update(favouriteMovie: FavouriteMovie): Observable<FavouriteMovie> {
        const copy = this.convert(favouriteMovie);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<FavouriteMovie> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    favMovieUser(id: number){
        return this.http.get(`${this.resourceUrl}/movieId/${id}`).map((res: Response) => {
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
     * Convert a returned JSON object to FavouriteMovie.
     */
    private convertItemFromServer(json: any): FavouriteMovie {
        const entity: FavouriteMovie = Object.assign(new FavouriteMovie(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a FavouriteMovie to a JSON which can be sent to the server.
     */
    private convert(favouriteMovie: FavouriteMovie): FavouriteMovie {
        const copy: FavouriteMovie = Object.assign({}, favouriteMovie);

        copy.date = this.dateUtils.toDate(favouriteMovie.date);
        return copy;
    }
}
