/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoDocsTestModule } from '../../../test.module';
import { ShippingOrderComponent } from 'app/entities/shipping-order/shipping-order.component';
import { ShippingOrderService } from 'app/entities/shipping-order/shipping-order.service';
import { ShippingOrder } from 'app/shared/model/shipping-order.model';

describe('Component Tests', () => {
  describe('ShippingOrder Management Component', () => {
    let comp: ShippingOrderComponent;
    let fixture: ComponentFixture<ShippingOrderComponent>;
    let service: ShippingOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoDocsTestModule],
        declarations: [ShippingOrderComponent],
        providers: []
      })
        .overrideTemplate(ShippingOrderComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShippingOrderComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShippingOrderService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ShippingOrder(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.shippingOrders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
