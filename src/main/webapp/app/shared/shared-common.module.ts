import { NgModule } from '@angular/core';

import { SoDocsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [SoDocsSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [SoDocsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SoDocsSharedCommonModule {}
