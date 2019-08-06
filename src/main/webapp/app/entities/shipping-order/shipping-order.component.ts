import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IShippingOrder } from 'app/shared/model/shipping-order.model';
import { AccountService } from 'app/core';
import { ShippingOrderService } from './shipping-order.service';

@Component({
  selector: 'jhi-shipping-order',
  templateUrl: './shipping-order.component.html'
})
export class ShippingOrderComponent implements OnInit, OnDestroy {
  shippingOrders: IShippingOrder[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected shippingOrderService: ShippingOrderService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.shippingOrderService
      .query()
      .pipe(
        filter((res: HttpResponse<IShippingOrder[]>) => res.ok),
        map((res: HttpResponse<IShippingOrder[]>) => res.body)
      )
      .subscribe(
        (res: IShippingOrder[]) => {
          this.shippingOrders = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInShippingOrders();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IShippingOrder) {
    return item.id;
  }

  registerChangeInShippingOrders() {
    this.eventSubscriber = this.eventManager.subscribe('shippingOrderListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
