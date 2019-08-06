import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShippingOrder } from 'app/shared/model/shipping-order.model';

@Component({
  selector: 'jhi-shipping-order-detail',
  templateUrl: './shipping-order-detail.component.html'
})
export class ShippingOrderDetailComponent implements OnInit {
  shippingOrder: IShippingOrder;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shippingOrder }) => {
      this.shippingOrder = shippingOrder;
    });
  }

  previousState() {
    window.history.back();
  }
}
