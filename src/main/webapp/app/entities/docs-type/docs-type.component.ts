import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDocsType } from 'app/shared/model/docs-type.model';
import { AccountService } from 'app/core';
import { DocsTypeService } from './docs-type.service';

@Component({
  selector: 'jhi-docs-type',
  templateUrl: './docs-type.component.html'
})
export class DocsTypeComponent implements OnInit, OnDestroy {
  docsTypes: IDocsType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected docsTypeService: DocsTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.docsTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IDocsType[]>) => res.ok),
        map((res: HttpResponse<IDocsType[]>) => res.body)
      )
      .subscribe(
        (res: IDocsType[]) => {
          this.docsTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDocsTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDocsType) {
    return item.id;
  }

  registerChangeInDocsTypes() {
    this.eventSubscriber = this.eventManager.subscribe('docsTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
