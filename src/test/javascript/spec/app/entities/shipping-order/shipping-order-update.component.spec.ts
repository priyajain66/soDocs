/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SoDocsTestModule } from '../../../test.module';
import { ShippingOrderUpdateComponent } from 'app/entities/shipping-order/shipping-order-update.component';
import { ShippingOrderService } from 'app/entities/shipping-order/shipping-order.service';
import { ShippingOrder } from 'app/shared/model/shipping-order.model';

describe('Component Tests', () => {
  describe('ShippingOrder Management Update Component', () => {
    let comp: ShippingOrderUpdateComponent;
    let fixture: ComponentFixture<ShippingOrderUpdateComponent>;
    let service: ShippingOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoDocsTestModule],
        declarations: [ShippingOrderUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ShippingOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShippingOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShippingOrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShippingOrder(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShippingOrder();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
