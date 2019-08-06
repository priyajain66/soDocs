import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISoDocs } from 'app/shared/model/so-docs.model';
import { SoDocsService } from './so-docs.service';

@Component({
  selector: 'jhi-so-docs-delete-dialog',
  templateUrl: './so-docs-delete-dialog.component.html'
})
export class SoDocsDeleteDialogComponent {
  soDocs: ISoDocs;

  constructor(protected soDocsService: SoDocsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.soDocsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'soDocsListModification',
        content: 'Deleted an soDocs'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-so-docs-delete-popup',
  template: ''
})
export class SoDocsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ soDocs }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SoDocsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.soDocs = soDocs;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/so-docs', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/so-docs', { outlets: { popup: null } }]);
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
