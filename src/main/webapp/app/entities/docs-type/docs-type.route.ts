import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DocsType } from 'app/shared/model/docs-type.model';
import { DocsTypeService } from './docs-type.service';
import { DocsTypeComponent } from './docs-type.component';
import { DocsTypeDetailComponent } from './docs-type-detail.component';
import { DocsTypeUpdateComponent } from './docs-type-update.component';
import { DocsTypeDeletePopupComponent } from './docs-type-delete-dialog.component';
import { IDocsType } from 'app/shared/model/docs-type.model';

@Injectable({ providedIn: 'root' })
export class DocsTypeResolve implements Resolve<IDocsType> {
  constructor(private service: DocsTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDocsType> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DocsType>) => response.ok),
        map((docsType: HttpResponse<DocsType>) => docsType.body)
      );
    }
    return of(new DocsType());
  }
}

export const docsTypeRoute: Routes = [
  {
    path: '',
    component: DocsTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DocsTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DocsTypeDetailComponent,
    resolve: {
      docsType: DocsTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DocsTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DocsTypeUpdateComponent,
    resolve: {
      docsType: DocsTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DocsTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DocsTypeUpdateComponent,
    resolve: {
      docsType: DocsTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DocsTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const docsTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DocsTypeDeletePopupComponent,
    resolve: {
      docsType: DocsTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DocsTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
