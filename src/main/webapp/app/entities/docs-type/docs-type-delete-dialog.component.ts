import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocsType } from 'app/shared/model/docs-type.model';
import { DocsTypeService } from './docs-type.service';

@Component({
  selector: 'jhi-docs-type-delete-dialog',
  templateUrl: './docs-type-delete-dialog.component.html'
})
export class DocsTypeDeleteDialogComponent {
  docsType: IDocsType;

  constructor(protected docsTypeService: DocsTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.docsTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'docsTypeListModification',
        content: 'Deleted an docsType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-docs-type-delete-popup',
  template: ''
})
export class DocsTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ docsType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DocsTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.docsType = docsType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/docs-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/docs-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
