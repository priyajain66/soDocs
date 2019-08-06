import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISoDocs } from 'app/shared/model/so-docs.model';

type EntityResponseType = HttpResponse<ISoDocs>;
type EntityArrayResponseType = HttpResponse<ISoDocs[]>;

@Injectable({ providedIn: 'root' })
export class SoDocsService {
  public resourceUrl = SERVER_API_URL + 'api/so-docs';

  constructor(protected http: HttpClient) {}

  create(soDocs: ISoDocs): Observable<EntityResponseType> {
    return this.http.post<ISoDocs>(this.resourceUrl, soDocs, { observe: 'response' });
  }

  update(soDocs: ISoDocs): Observable<EntityResponseType> {
    return this.http.put<ISoDocs>(this.resourceUrl, soDocs, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISoDocs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISoDocs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
