import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocsType } from 'app/shared/model/docs-type.model';

type EntityResponseType = HttpResponse<IDocsType>;
type EntityArrayResponseType = HttpResponse<IDocsType[]>;

@Injectable({ providedIn: 'root' })
export class DocsTypeService {
  public resourceUrl = SERVER_API_URL + 'api/docs-types';

  constructor(protected http: HttpClient) {}

  create(docsType: IDocsType): Observable<EntityResponseType> {
    return this.http.post<IDocsType>(this.resourceUrl, docsType, { observe: 'response' });
  }

  update(docsType: IDocsType): Observable<EntityResponseType> {
    return this.http.put<IDocsType>(this.resourceUrl, docsType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDocsType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDocsType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
