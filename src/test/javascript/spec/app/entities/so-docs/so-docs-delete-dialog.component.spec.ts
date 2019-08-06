/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoDocsTestModule } from '../../../test.module';
import { SoDocsDeleteDialogComponent } from 'app/entities/so-docs/so-docs-delete-dialog.component';
import { SoDocsService } from 'app/entities/so-docs/so-docs.service';

describe('Component Tests', () => {
  describe('SoDocs Management Delete Component', () => {
    let comp: SoDocsDeleteDialogComponent;
    let fixture: ComponentFixture<SoDocsDeleteDialogComponent>;
    let service: SoDocsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SoDocsTestModule],
        declarations: [SoDocsDeleteDialogComponent]
      })
        .overrideTemplate(SoDocsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SoDocsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SoDocsService);
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
