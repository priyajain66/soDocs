/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoDocsTestModule } from '../../../test.module';
import { DocsTypeDetailComponent } from 'app/entities/docs-type/docs-type-detail.component';
import { DocsType } from 'app/shared/model/docs-type.model';

describe('Component Tests', () => {
  describe('DocsType Management Detail Component', () => {
    let comp: DocsTypeDetailComponent;
    let fixture: ComponentFixture<DocsTypeDetailComponent>;
    const route = ({ data: of({ docsType: new DocsType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoDocsTestModule],
        declarations: [DocsTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DocsTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocsTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.docsType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
