/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SoDocsTestModule } from '../../../test.module';
import { SoDocsUpdateComponent } from 'app/entities/so-docs/so-docs-update.component';
import { SoDocsService } from 'app/entities/so-docs/so-docs.service';
import { SoDocs } from 'app/shared/model/so-docs.model';

describe('Component Tests', () => {
  describe('SoDocs Management Update Component', () => {
    let comp: SoDocsUpdateComponent;
    let fixture: ComponentFixture<SoDocsUpdateComponent>;
    let service: SoDocsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoDocsTestModule],
        declarations: [SoDocsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SoDocsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SoDocsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SoDocsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SoDocs(123);
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
        const entity = new SoDocs();
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
