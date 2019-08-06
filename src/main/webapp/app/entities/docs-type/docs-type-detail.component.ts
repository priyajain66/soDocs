import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocsType } from 'app/shared/model/docs-type.model';

@Component({
  selector: 'jhi-docs-type-detail',
  templateUrl: './docs-type-detail.component.html'
})
export class DocsTypeDetailComponent implements OnInit {
  docsType: IDocsType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ docsType }) => {
      this.docsType = docsType;
    });
  }

  previousState() {
    window.history.back();
  }
}
