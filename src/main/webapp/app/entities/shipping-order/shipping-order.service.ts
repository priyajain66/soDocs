import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IShippingOrder } from 'app/shared/model/shipping-order.model';

type EntityResponseType = HttpResponse<IShippingOrder>;
type EntityArrayResponseType = HttpResponse<IShippingOrder[]>;

@Injectable({ providedIn: 'root' })
export class ShippingOrderService {
  public resourceUrl = SERVER_API_URL + 'api/shipping-orders';

  constructor(protected http: HttpClient) {}

  create(shippingOrder: IShippingOrder): Observable<EntityResponseType> {
    return this.http.post<IShippingOrder>(this.resourceUrl, shippingOrder, { observe: 'response' });
  }

  update(shippingOrder: IShippingOrder): Observable<EntityResponseType> {
    return this.http.put<IShippingOrder>(this.resourceUrl, shippingOrder, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShippingOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShippingOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
