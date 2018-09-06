import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { BankAccountComponentsPage, BankAccountUpdatePage } from './bank-account.page-object';

describe('BankAccount e2e test', () => {
    let navBarPage: NavBarPage;
    let bankAccountUpdatePage: BankAccountUpdatePage;
    let bankAccountComponentsPage: BankAccountComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load BankAccounts', () => {
        navBarPage.goToEntity('bank-account');
        bankAccountComponentsPage = new BankAccountComponentsPage();
        expect(bankAccountComponentsPage.getTitle()).toMatch(/Bank Accounts/);
    });

    it('should load create BankAccount page', () => {
        bankAccountComponentsPage.clickOnCreateButton();
        bankAccountUpdatePage = new BankAccountUpdatePage();
        expect(bankAccountUpdatePage.getPageTitle()).toMatch(/Create or edit a Bank Account/);
        bankAccountUpdatePage.cancel();
    });

    it('should create and save BankAccounts', () => {
        bankAccountComponentsPage.clickOnCreateButton();
        bankAccountUpdatePage.setUsernameInput('username');
        expect(bankAccountUpdatePage.getUsernameInput()).toMatch('username');
        bankAccountUpdatePage.setPasswordInput('password');
        expect(bankAccountUpdatePage.getPasswordInput()).toMatch('password');
        bankAccountUpdatePage.setFirstNameInput('firstName');
        expect(bankAccountUpdatePage.getFirstNameInput()).toMatch('firstName');
        bankAccountUpdatePage.setLastNameInput('lastName');
        expect(bankAccountUpdatePage.getLastNameInput()).toMatch('lastName');
        bankAccountUpdatePage.setEmailInput('email');
        expect(bankAccountUpdatePage.getEmailInput()).toMatch('email');
        bankAccountUpdatePage.setPhoneInput('phone');
        expect(bankAccountUpdatePage.getPhoneInput()).toMatch('phone');
        bankAccountUpdatePage.setAddressInput('address');
        expect(bankAccountUpdatePage.getAddressInput()).toMatch('address');
        bankAccountUpdatePage.setPostalCodeInput('postalCode');
        expect(bankAccountUpdatePage.getPostalCodeInput()).toMatch('postalCode');
        bankAccountUpdatePage.setPanNumberInput('panNumber');
        expect(bankAccountUpdatePage.getPanNumberInput()).toMatch('panNumber');
        bankAccountUpdatePage.save();
        expect(bankAccountUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
