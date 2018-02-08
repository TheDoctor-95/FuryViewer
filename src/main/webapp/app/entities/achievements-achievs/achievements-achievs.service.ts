import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { AchievementsAchievs } from './achievements-achievs.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AchievementsAchievsService {

    private resourceUrl = SERVER_API_URL + 'api/achievements-achievs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(achievementsAchievs: AchievementsAchievs): Observable<AchievementsAchievs> {
        const copy = this.convert(achievementsAchievs);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(achievementsAchievs: AchievementsAchievs): Observable<AchievementsAchievs> {
        const copy = this.convert(achievementsAchievs);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AchievementsAchievs> {
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
     * Convert a returned JSON object to AchievementsAchievs.
     */
    private convertItemFromServer(json: any): AchievementsAchievs {
        const entity: AchievementsAchievs = Object.assign(new AchievementsAchievs(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a AchievementsAchievs to a JSON which can be sent to the server.
     */
    private convert(achievementsAchievs: AchievementsAchievs): AchievementsAchievs {
        const copy: AchievementsAchievs = Object.assign({}, achievementsAchievs);

        copy.date = this.dateUtils.toDate(achievementsAchievs.date);
        return copy;
    }
}
