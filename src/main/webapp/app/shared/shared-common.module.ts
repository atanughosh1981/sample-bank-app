import { NgModule } from '@angular/core';

import { BankSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [BankSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [BankSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class BankSharedCommonModule {}
