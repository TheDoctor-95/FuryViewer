import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ChapterSeen } from './chapter-seen.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ChapterSeenService {

    private resourceUrl = SERVER_API_URL + 'api/chapter-seens';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(chapterSeen: ChapterSeen): Observable<ChapterSeen> {
        const copy = this.convert(chapterSeen);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(chapterSeen: ChapterSeen): Observable<ChapterSeen> {
        const copy = this.convert(chapterSeen);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ChapterSeen> {
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
     * Convert a returned JSON object to ChapterSeen.
     */
    private convertItemFromServer(json: any): ChapterSeen {
        const entity: ChapterSeen = Object.assign(new ChapterSeen(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a ChapterSeen to a JSON which can be sent to the server.
     */
    private convert(chapterSeen: ChapterSeen): ChapterSeen {
        const copy: ChapterSeen = Object.assign({}, chapterSeen);

        copy.date = this.dateUtils.toDate(chapterSeen.date);
        return copy;
    }
}
