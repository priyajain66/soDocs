/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoDocsTestModule } from '../../../test.module';
import { ShippingOrderDeleteDialogComponent } from 'app/entities/shipping-order/shipping-order-delete-dialog.component';
import { ShippingOrderService } from 'app/entities/shipping-order/shipping-order.service';

describe('Component Tests', () => {
  describe('ShippingOrder Management Delete Component', () => {
    let comp: ShippingOrderDeleteDialogComponent;
    let fixture: ComponentFixture<ShippingOrderDeleteDialogComponent>;
    let service: ShippingOrderService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoDocsTestModule],
        declarations: [ShippingOrderDeleteDialogComponent]
      })
        .overrideTemplate(ShippingOrderDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShippingOrderDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShippingOrderService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
