/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoDocsTestModule } from '../../../test.module';
import { DocsTypeComponent } from 'app/entities/docs-type/docs-type.component';
import { DocsTypeService } from 'app/entities/docs-type/docs-type.service';
import { DocsType } from 'app/shared/model/docs-type.model';

describe('Component Tests', () => {
  describe('DocsType Management Component', () => {
    let comp: DocsTypeComponent;
    let fixture: ComponentFixture<DocsTypeComponent>;
    let service: DocsTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoDocsTestModule],
        declarations: [DocsTypeComponent],
        providers: []
      })
        .overrideTemplate(DocsTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocsTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocsTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DocsType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.docsTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
