import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ShippingOrder } from 'app/shared/model/shipping-order.model';
import { ShippingOrderService } from './shipping-order.service';
import { ShippingOrderComponent } from './shipping-order.component';
import { ShippingOrderDetailComponent } from './shipping-order-detail.component';
import { ShippingOrderUpdateComponent } from './shipping-order-update.component';
import { ShippingOrderDeletePopupComponent } from './shipping-order-delete-dialog.component';
import { IShippingOrder } from 'app/shared/model/shipping-order.model';

@Injectable({ providedIn: 'root' })
export class ShippingOrderResolve implements Resolve<IShippingOrder> {
  constructor(private service: ShippingOrderService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IShippingOrder> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ShippingOrder>) => response.ok),
        map((shippingOrder: HttpResponse<ShippingOrder>) => shippingOrder.body)
      );
    }
    return of(new ShippingOrder());
  }
}

export const shippingOrderRoute: Routes = [
  {
    path: '',
    component: ShippingOrderComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ShippingOrders'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ShippingOrderDetailComponent,
    resolve: {
      shippingOrder: ShippingOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ShippingOrders'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ShippingOrderUpdateComponent,
    resolve: {
      shippingOrder: ShippingOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ShippingOrders'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ShippingOrderUpdateComponent,
    resolve: {
      shippingOrder: ShippingOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ShippingOrders'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const shippingOrderPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ShippingOrderDeletePopupComponent,
    resolve: {
      shippingOrder: ShippingOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ShippingOrders'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
