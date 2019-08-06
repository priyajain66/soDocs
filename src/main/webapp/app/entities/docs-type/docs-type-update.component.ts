import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDocsType, DocsType } from 'app/shared/model/docs-type.model';
import { DocsTypeService } from './docs-type.service';

@Component({
  selector: 'jhi-docs-type-update',
  templateUrl: './docs-type-update.component.html'
})
export class DocsTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(protected docsTypeService: DocsTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ docsType }) => {
      this.updateForm(docsType);
    });
  }

  updateForm(docsType: IDocsType) {
    this.editForm.patchValue({
      id: docsType.id,
      name: docsType.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const docsType = this.createFromForm();
    if (docsType.id !== undefined) {
      this.subscribeToSaveResponse(this.docsTypeService.update(docsType));
    } else {
      this.subscribeToSaveResponse(this.docsTypeService.create(docsType));
    }
  }

  private createFromForm(): IDocsType {
    return {
      ...new DocsType(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocsType>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
