import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ISoDocs } from 'app/shared/model/so-docs.model';
import { AccountService } from 'app/core';
import { SoDocsService } from './so-docs.service';

@Component({
  selector: 'jhi-so-docs',
  templateUrl: './so-docs.component.html'
})
export class SoDocsComponent implements OnInit, OnDestroy {
  soDocs: ISoDocs[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected soDocsService: SoDocsService,
    protected jhiAlertService: JhiAlertService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.soDocsService
      .query()
      .pipe(
        filter((res: HttpResponse<ISoDocs[]>) => res.ok),
        map((res: HttpResponse<ISoDocs[]>) => res.body)
      )
      .subscribe(
        (res: ISoDocs[]) => {
          this.soDocs = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSoDocs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISoDocs) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInSoDocs() {
    this.eventSubscriber = this.eventManager.subscribe('soDocsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
