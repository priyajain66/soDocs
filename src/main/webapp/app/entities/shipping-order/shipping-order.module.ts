import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoDocsSharedModule } from 'app/shared';
import {
  ShippingOrderComponent,
  ShippingOrderDetailComponent,
  ShippingOrderUpdateComponent,
  ShippingOrderDeletePopupComponent,
  ShippingOrderDeleteDialogComponent,
  shippingOrderRoute,
  shippingOrderPopupRoute
} from './';

const ENTITY_STATES = [...shippingOrderRoute, ...shippingOrderPopupRoute];

@NgModule({
  imports: [SoDocsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ShippingOrderComponent,
    ShippingOrderDetailComponent,
    ShippingOrderUpdateComponent,
    ShippingOrderDeleteDialogComponent,
    ShippingOrderDeletePopupComponent
  ],
  entryComponents: [
    ShippingOrderComponent,
    ShippingOrderUpdateComponent,
    ShippingOrderDeleteDialogComponent,
    ShippingOrderDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoDocsShippingOrderModule {}
