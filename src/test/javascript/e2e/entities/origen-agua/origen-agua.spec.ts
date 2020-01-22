import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrigenAguaComponentsPage, OrigenAguaDeleteDialog, OrigenAguaUpdatePage } from './origen-agua.page-object';

const expect = chai.expect;

describe('OrigenAgua e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let origenAguaComponentsPage: OrigenAguaComponentsPage;
  let origenAguaUpdatePage: OrigenAguaUpdatePage;
  let origenAguaDeleteDialog: OrigenAguaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OrigenAguas', async () => {
    await navBarPage.goToEntity('origen-agua');
    origenAguaComponentsPage = new OrigenAguaComponentsPage();
    await browser.wait(ec.visibilityOf(origenAguaComponentsPage.title), 5000);
    expect(await origenAguaComponentsPage.getTitle()).to.eq('testmono04App.origenAgua.home.title');
  });

  it('should load create OrigenAgua page', async () => {
    await origenAguaComponentsPage.clickOnCreateButton();
    origenAguaUpdatePage = new OrigenAguaUpdatePage();
    expect(await origenAguaUpdatePage.getPageTitle()).to.eq('testmono04App.origenAgua.home.createOrEditLabel');
    await origenAguaUpdatePage.cancel();
  });

  it('should create and save OrigenAguas', async () => {
    const nbButtonsBeforeCreate = await origenAguaComponentsPage.countDeleteButtons();

    await origenAguaComponentsPage.clickOnCreateButton();
    await promise.all([origenAguaUpdatePage.setNombreInput('nombre')]);
    expect(await origenAguaUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    const selectedEstado = origenAguaUpdatePage.getEstadoInput();
    if (await selectedEstado.isSelected()) {
      await origenAguaUpdatePage.getEstadoInput().click();
      expect(await origenAguaUpdatePage.getEstadoInput().isSelected(), 'Expected estado not to be selected').to.be.false;
    } else {
      await origenAguaUpdatePage.getEstadoInput().click();
      expect(await origenAguaUpdatePage.getEstadoInput().isSelected(), 'Expected estado to be selected').to.be.true;
    }
    await origenAguaUpdatePage.save();
    expect(await origenAguaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await origenAguaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last OrigenAgua', async () => {
    const nbButtonsBeforeDelete = await origenAguaComponentsPage.countDeleteButtons();
    await origenAguaComponentsPage.clickOnLastDeleteButton();

    origenAguaDeleteDialog = new OrigenAguaDeleteDialog();
    expect(await origenAguaDeleteDialog.getDialogTitle()).to.eq('testmono04App.origenAgua.delete.question');
    await origenAguaDeleteDialog.clickOnConfirmButton();

    expect(await origenAguaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
