import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Artist } from './artist.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { FilmographyArtistModel } from "../../shared/model/filmographyArtist.model";

@Injectable()
export class ArtistService {

    private resourceUrl = SERVER_API_URL + 'api/artists';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(artist: Artist): Observable<Artist> {
        const copy = this.convert(artist);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(artist: Artist): Observable<Artist> {
        const copy = this.convert(artist);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Artist> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    filmography(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/${id}/moviesAndSeriesOrderedByDate/`)
            .map((res: Response) => this.convertFilmographyResponse(res));
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findMovieActors(id: number): Observable<ResponseWrapper> {

        return this.http.get(`${SERVER_API_URL}/api/movies/${id}/actors`)
            .map((res: Response) => this.convertResponse(res));
    }

    seriesActorsQuery(id: number): Observable<ResponseWrapper> {

        return this.http.get(`${SERVER_API_URL}/api/ActorBySeriesID/${id}`)
            .map((res: Response) => this.convertResponse(res));
    }
    seriesDirectorQuery(id: number): Observable<Artist> {
        return this.http.get(`${SERVER_API_URL}/api/DirectorBySeriesID/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }
    seriesScriptWriterQuery(id: number): Observable<Artist> {
        return this.http.get(`${SERVER_API_URL}/api/ScripwiterBySeriesID/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
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

    private convertFilmographyResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertFilmographyFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Artist.
     */
    private convertItemFromServer(json: any): Artist {
        const entity: Artist = Object.assign(new Artist(), json);
        entity.birthdate = this.dateUtils
            .convertLocalDateFromServer(json.birthdate);
        entity.deathdate = this.dateUtils
            .convertLocalDateFromServer(json.deathdate);
        return entity;
    }

    private convertFilmographyFromServer(json: any): FilmographyArtistModel {
        const entity: FilmographyArtistModel = Object.assign(new FilmographyArtistModel(), json);
        return entity;
    }

    /**
     * Convert a Artist to a JSON which can be sent to the server.
     */
    private convert(artist: Artist): Artist {
        const copy: Artist = Object.assign({}, artist);
        copy.birthdate = this.dateUtils
            .convertLocalDateToServer(artist.birthdate);
        copy.deathdate = this.dateUtils
            .convertLocalDateToServer(artist.deathdate);
        return copy;
    }


}
