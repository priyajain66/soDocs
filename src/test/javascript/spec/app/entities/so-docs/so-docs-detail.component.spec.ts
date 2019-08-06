/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoDocsTestModule } from '../../../test.module';
import { SoDocsDetailComponent } from 'app/entities/so-docs/so-docs-detail.component';
import { SoDocs } from 'app/shared/model/so-docs.model';

describe('Component Tests', () => {
  describe('SoDocs Management Detail Component', () => {
    let comp: SoDocsDetailComponent;
    let fixture: ComponentFixture<SoDocsDetailComponent>;
    const route = ({ data: of({ soDocs: new SoDocs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoDocsTestModule],
        declarations: [SoDocsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SoDocsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SoDocsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.soDocs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
