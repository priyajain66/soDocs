import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SoDocsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [SoDocsSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [SoDocsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoDocsSharedModule {
  static forRoot() {
    return {
      ngModule: SoDocsSharedModule
    };
  }
}
