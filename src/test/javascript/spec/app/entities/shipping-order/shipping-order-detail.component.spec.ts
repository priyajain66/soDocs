/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoDocsTestModule } from '../../../test.module';
import { ShippingOrderDetailComponent } from 'app/entities/shipping-order/shipping-order-detail.component';
import { ShippingOrder } from 'app/shared/model/shipping-order.model';

describe('Component Tests', () => {
  describe('ShippingOrder Management Detail Component', () => {
    let comp: ShippingOrderDetailComponent;
    let fixture: ComponentFixture<ShippingOrderDetailComponent>;
    const route = ({ data: of({ shippingOrder: new ShippingOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoDocsTestModule],
        declarations: [ShippingOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ShippingOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShippingOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shippingOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
