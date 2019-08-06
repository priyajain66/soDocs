import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ISoDocs, SoDocs } from 'app/shared/model/so-docs.model';
import { SoDocsService } from './so-docs.service';
import { IShippingOrder } from 'app/shared/model/shipping-order.model';
import { ShippingOrderService } from 'app/entities/shipping-order';
import { IDocsType } from 'app/shared/model/docs-type.model';
import { DocsTypeService } from 'app/entities/docs-type';

@Component({
  selector: 'jhi-so-docs-update',
  templateUrl: './so-docs-update.component.html'
})
export class SoDocsUpdateComponent implements OnInit {
  isSaving: boolean;

  shippingorders: IShippingOrder[];

  docstypes: IDocsType[];

  editForm = this.fb.group({
    id: [],
    fileName: [],
    uploadFile: [],
    uploadFileContentType: [],
    uploadFileContentType: [],
    privateMode: [],
    shippingOrderId: [],
    docsTypeId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected soDocsService: SoDocsService,
    protected shippingOrderService: ShippingOrderService,
    protected docsTypeService: DocsTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ soDocs }) => {
      this.updateForm(soDocs);
    });
    this.shippingOrderService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IShippingOrder[]>) => mayBeOk.ok),
        map((response: HttpResponse<IShippingOrder[]>) => response.body)
      )
      .subscribe((res: IShippingOrder[]) => (this.shippingorders = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.docsTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDocsType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDocsType[]>) => response.body)
      )
      .subscribe((res: IDocsType[]) => (this.docstypes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(soDocs: ISoDocs) {
    this.editForm.patchValue({
      id: soDocs.id,
      fileName: soDocs.fileName,
      uploadFile: soDocs.uploadFile,
      uploadFileContentType: soDocs.uploadFileContentType,
      uploadFileContentType: soDocs.uploadFileContentType,
      privateMode: soDocs.privateMode,
      shippingOrderId: soDocs.shippingOrderId,
      docsTypeId: soDocs.docsTypeId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const soDocs = this.createFromForm();
    if (soDocs.id !== undefined) {
      this.subscribeToSaveResponse(this.soDocsService.update(soDocs));
    } else {
      this.subscribeToSaveResponse(this.soDocsService.create(soDocs));
    }
  }

  private createFromForm(): ISoDocs {
    return {
      ...new SoDocs(),
      id: this.editForm.get(['id']).value,
      fileName: this.editForm.get(['fileName']).value,
      uploadFileContentType: this.editForm.get(['uploadFileContentType']).value,
      uploadFile: this.editForm.get(['uploadFile']).value,
      uploadFileContentType: this.editForm.get(['uploadFileContentType']).value,
      privateMode: this.editForm.get(['privateMode']).value,
      shippingOrderId: this.editForm.get(['shippingOrderId']).value,
      docsTypeId: this.editForm.get(['docsTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISoDocs>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackShippingOrderById(index: number, item: IShippingOrder) {
    return item.id;
  }

  trackDocsTypeById(index: number, item: IDocsType) {
    return item.id;
  }
}
