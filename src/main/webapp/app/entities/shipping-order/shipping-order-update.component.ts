import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IShippingOrder, ShippingOrder } from 'app/shared/model/shipping-order.model';
import { ShippingOrderService } from './shipping-order.service';

@Component({
  selector: 'jhi-shipping-order-update',
  templateUrl: './shipping-order-update.component.html'
})
export class ShippingOrderUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: []
  });

  constructor(protected shippingOrderService: ShippingOrderService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ shippingOrder }) => {
      this.updateForm(shippingOrder);
    });
  }

  updateForm(shippingOrder: IShippingOrder) {
    this.editForm.patchValue({
      id: shippingOrder.id
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const shippingOrder = this.createFromForm();
    if (shippingOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.shippingOrderService.update(shippingOrder));
    } else {
      this.subscribeToSaveResponse(this.shippingOrderService.create(shippingOrder));
    }
  }

  private createFromForm(): IShippingOrder {
    return {
      ...new ShippingOrder(),
      id: this.editForm.get(['id']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShippingOrder>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
