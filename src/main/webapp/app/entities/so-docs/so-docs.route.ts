import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SoDocs } from 'app/shared/model/so-docs.model';
import { SoDocsService } from './so-docs.service';
import { SoDocsComponent } from './so-docs.component';
import { SoDocsDetailComponent } from './so-docs-detail.component';
import { SoDocsUpdateComponent } from './so-docs-update.component';
import { SoDocsDeletePopupComponent } from './so-docs-delete-dialog.component';
import { ISoDocs } from 'app/shared/model/so-docs.model';

@Injectable({ providedIn: 'root' })
export class SoDocsResolve implements Resolve<ISoDocs> {
  constructor(private service: SoDocsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISoDocs> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SoDocs>) => response.ok),
        map((soDocs: HttpResponse<SoDocs>) => soDocs.body)
      );
    }
    return of(new SoDocs());
  }
}

export const soDocsRoute: Routes = [
  {
    path: '',
    component: SoDocsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SoDocs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SoDocsDetailComponent,
    resolve: {
      soDocs: SoDocsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SoDocs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SoDocsUpdateComponent,
    resolve: {
      soDocs: SoDocsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SoDocs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SoDocsUpdateComponent,
    resolve: {
      soDocs: SoDocsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SoDocs'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const soDocsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SoDocsDeletePopupComponent,
    resolve: {
      soDocs: SoDocsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SoDocs'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
