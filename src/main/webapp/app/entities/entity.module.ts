import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BankBankAccountModule } from './bank-account/bank-account.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        BankBankAccountModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BankEntityModule {}
