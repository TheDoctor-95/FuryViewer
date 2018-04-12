import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Main } from './main.model';
import { createRequestOption } from '../../shared';

export type MainResponseType = HttpResponse<Main>;
export type MainArrayResponseType = HttpResponse<Main[]>;

@Injectable()
export class MainService {

    private resourceUrl = SERVER_API_URL + 'api/main/main';

    constructor(private http: HttpClient) { }

    query(req?: any): Observable<MainResponseType> {
        const options = createRequestOption(req);
        return this.http.get<Main>(this.resourceUrl, { observe: 'response' })
            .map((res: MainResponseType) => this.convertResponse(res));
    }

    private convertResponse(res: MainResponseType): MainResponseType {
        const body: Main = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: MainArrayResponseType): MainArrayResponseType {
        const jsonResponse: Main[] = res.body;
        const body: Main[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Main.
     */
    private convertItemFromServer(json: any): Main {
        const copy: Main = Object.assign(new Main(), json);
        return copy;
    }

    /**
     * Convert a Main to a JSON which can be sent to the server.
     */
    private convert(main: Main): Main {
        const copy: Main = Object.assign({}, main);
        return copy;
    }
}
