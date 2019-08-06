/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SoDocsTestModule } from '../../../test.module';
import { DocsTypeUpdateComponent } from 'app/entities/docs-type/docs-type-update.component';
import { DocsTypeService } from 'app/entities/docs-type/docs-type.service';
import { DocsType } from 'app/shared/model/docs-type.model';

describe('Component Tests', () => {
  describe('DocsType Management Update Component', () => {
    let comp: DocsTypeUpdateComponent;
    let fixture: ComponentFixture<DocsTypeUpdateComponent>;
    let service: DocsTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoDocsTestModule],
        declarations: [DocsTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DocsTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocsTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocsTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocsType(123);
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
        const entity = new DocsType();
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
