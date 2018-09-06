import { element, by, promise, ElementFinder } from 'protractor';

export class BankAccountComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-bank-account div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class BankAccountUpdatePage {
    pageTitle = element(by.id('jhi-bank-account-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    usernameInput = element(by.id('field_username'));
    passwordInput = element(by.id('field_password'));
    firstNameInput = element(by.id('field_firstName'));
    lastNameInput = element(by.id('field_lastName'));
    emailInput = element(by.id('field_email'));
    phoneInput = element(by.id('field_phone'));
    addressInput = element(by.id('field_address'));
    postalCodeInput = element(by.id('field_postalCode'));
    panNumberInput = element(by.id('field_panNumber'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setUsernameInput(username): promise.Promise<void> {
        return this.usernameInput.sendKeys(username);
    }

    getUsernameInput() {
        return this.usernameInput.getAttribute('value');
    }

    setPasswordInput(password): promise.Promise<void> {
        return this.passwordInput.sendKeys(password);
    }

    getPasswordInput() {
        return this.passwordInput.getAttribute('value');
    }

    setFirstNameInput(firstName): promise.Promise<void> {
        return this.firstNameInput.sendKeys(firstName);
    }

    getFirstNameInput() {
        return this.firstNameInput.getAttribute('value');
    }

    setLastNameInput(lastName): promise.Promise<void> {
        return this.lastNameInput.sendKeys(lastName);
    }

    getLastNameInput() {
        return this.lastNameInput.getAttribute('value');
    }

    setEmailInput(email): promise.Promise<void> {
        return this.emailInput.sendKeys(email);
    }

    getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    setPhoneInput(phone): promise.Promise<void> {
        return this.phoneInput.sendKeys(phone);
    }

    getPhoneInput() {
        return this.phoneInput.getAttribute('value');
    }

    setAddressInput(address): promise.Promise<void> {
        return this.addressInput.sendKeys(address);
    }

    getAddressInput() {
        return this.addressInput.getAttribute('value');
    }

    setPostalCodeInput(postalCode): promise.Promise<void> {
        return this.postalCodeInput.sendKeys(postalCode);
    }

    getPostalCodeInput() {
        return this.postalCodeInput.getAttribute('value');
    }

    setPanNumberInput(panNumber): promise.Promise<void> {
        return this.panNumberInput.sendKeys(panNumber);
    }

    getPanNumberInput() {
        return this.panNumberInput.getAttribute('value');
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
