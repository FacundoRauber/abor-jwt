import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ComunidadComponentsPage, ComunidadDeleteDialog, ComunidadUpdatePage } from './comunidad.page-object';

const expect = chai.expect;

describe('Comunidad e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let comunidadComponentsPage: ComunidadComponentsPage;
  let comunidadUpdatePage: ComunidadUpdatePage;
  let comunidadDeleteDialog: ComunidadDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Comunidads', async () => {
    await navBarPage.goToEntity('comunidad');
    comunidadComponentsPage = new ComunidadComponentsPage();
    await browser.wait(ec.visibilityOf(comunidadComponentsPage.title), 5000);
    expect(await comunidadComponentsPage.getTitle()).to.eq('testmono04App.comunidad.home.title');
  });

  it('should load create Comunidad page', async () => {
    await comunidadComponentsPage.clickOnCreateButton();
    comunidadUpdatePage = new ComunidadUpdatePage();
    expect(await comunidadUpdatePage.getPageTitle()).to.eq('testmono04App.comunidad.home.createOrEditLabel');
    await comunidadUpdatePage.cancel();
  });

  it('should create and save Comunidads', async () => {
    const nbButtonsBeforeCreate = await comunidadComponentsPage.countDeleteButtons();

    await comunidadComponentsPage.clickOnCreateButton();
    await promise.all([comunidadUpdatePage.setNombreInput('nombre')]);
    expect(await comunidadUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    const selectedEstado = comunidadUpdatePage.getEstadoInput();
    if (await selectedEstado.isSelected()) {
      await comunidadUpdatePage.getEstadoInput().click();
      expect(await comunidadUpdatePage.getEstadoInput().isSelected(), 'Expected estado not to be selected').to.be.false;
    } else {
      await comunidadUpdatePage.getEstadoInput().click();
      expect(await comunidadUpdatePage.getEstadoInput().isSelected(), 'Expected estado to be selected').to.be.true;
    }
    await comunidadUpdatePage.save();
    expect(await comunidadUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await comunidadComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Comunidad', async () => {
    const nbButtonsBeforeDelete = await comunidadComponentsPage.countDeleteButtons();
    await comunidadComponentsPage.clickOnLastDeleteButton();

    comunidadDeleteDialog = new ComunidadDeleteDialog();
    expect(await comunidadDeleteDialog.getDialogTitle()).to.eq('testmono04App.comunidad.delete.question');
    await comunidadDeleteDialog.clickOnConfirmButton();

    expect(await comunidadComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
