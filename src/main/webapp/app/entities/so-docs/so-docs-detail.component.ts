import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISoDocs } from 'app/shared/model/so-docs.model';

@Component({
  selector: 'jhi-so-docs-detail',
  templateUrl: './so-docs-detail.component.html'
})
export class SoDocsDetailComponent implements OnInit {
  soDocs: ISoDocs;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ soDocs }) => {
      this.soDocs = soDocs;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
