import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Movie } from './movie.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MovieService {

    private resourceUrl = SERVER_API_URL + 'api/movies';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(movie: Movie): Observable<Movie> {
        const copy = this.convert(movie);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(movie: Movie): Observable<Movie> {
        const copy = this.convert(movie);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }



    find(id: number): Observable<Movie> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }
    superQuery(criteria: string): Observable<ResponseWrapper> {
        return this.http.get(`api/movie-s?${criteria}`).map((res: Response) => {
            const jsonResponse = res.json();
            console.log(jsonResponse);
            return this.convertResponse(res);
        });
    }

    getFavHate(id: number): Observable<number> {
        return this.http.get(`${this.resourceUrl}/totalFavHate/${id}`).map((res: Response) => {
            return  res.json();
        });
    }

    getNumFavHate(id: number): Observable<number> {
        return this.http.get(`${this.resourceUrl}/sumaFavHate/${id}`).map((res: Response) => {
            return  res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    pendingMovies(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/Pending/`)
            .map((res: Response) => this.convertResponse(res));
    }

    pendingMovies5(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/Pending/5`)
            .map((res: Response) => this.convertResponse(res));
    }
    topMovies(): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/topPelis/`)
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
     * Convert a returned JSON object to Movie.
     */
    private convertItemFromServer(json: any): Movie {
        const entity: Movie = Object.assign(new Movie(), json);
        entity.releaseDate = this.dateUtils
             .convertLocalDateFromServer(json.releaseDate);
        entity.dvd_release = this.dateUtils
            .convertLocalDateFromServer(json.dvd_release);
        return entity;
    }

    /**
     * Convert a Movie to a JSON which can be sent to the server.
     */
    private convert(movie: Movie): Movie {
        const copy: Movie = Object.assign({}, movie);
        copy.releaseDate = this.dateUtils
            .convertLocalDateToServer(movie.releaseDate);
        return copy;
    }
}
