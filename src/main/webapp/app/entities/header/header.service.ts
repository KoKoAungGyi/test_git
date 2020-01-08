import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IHeader } from 'app/shared/model/header.model';

type EntityResponseType = HttpResponse<IHeader>;
type EntityArrayResponseType = HttpResponse<IHeader[]>;

@Injectable({ providedIn: 'root' })
export class HeaderService {
  public resourceUrl = SERVER_API_URL + 'api/headers';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/headers';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHeader>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHeader[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHeader[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
