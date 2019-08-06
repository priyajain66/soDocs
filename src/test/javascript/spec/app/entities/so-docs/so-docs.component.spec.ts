/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SoDocsTestModule } from '../../../test.module';
import { SoDocsComponent } from 'app/entities/so-docs/so-docs.component';
import { SoDocsService } from 'app/entities/so-docs/so-docs.service';
import { SoDocs } from 'app/shared/model/so-docs.model';

describe('Component Tests', () => {
  describe('SoDocs Management Component', () => {
    let comp: SoDocsComponent;
    let fixture: ComponentFixture<SoDocsComponent>;
    let service: SoDocsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoDocsTestModule],
        declarations: [SoDocsComponent],
        providers: []
      })
        .overrideTemplate(SoDocsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SoDocsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SoDocsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SoDocs(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.soDocs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
