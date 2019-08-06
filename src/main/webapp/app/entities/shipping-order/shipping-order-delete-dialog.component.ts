import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShippingOrder } from 'app/shared/model/shipping-order.model';
import { ShippingOrderService } from './shipping-order.service';

@Component({
  selector: 'jhi-shipping-order-delete-dialog',
  templateUrl: './shipping-order-delete-dialog.component.html'
})
export class ShippingOrderDeleteDialogComponent {
  shippingOrder: IShippingOrder;

  constructor(
    protected shippingOrderService: ShippingOrderService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.shippingOrderService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'shippingOrderListModification',
        content: 'Deleted an shippingOrder'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-shipping-order-delete-popup',
  template: ''
})
export class ShippingOrderDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shippingOrder }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ShippingOrderDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.shippingOrder = shippingOrder;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/shipping-order', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/shipping-order', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
