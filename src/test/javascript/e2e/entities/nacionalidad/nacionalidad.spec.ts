import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { NacionalidadComponentsPage, NacionalidadDeleteDialog, NacionalidadUpdatePage } from './nacionalidad.page-object';

const expect = chai.expect;

describe('Nacionalidad e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let nacionalidadComponentsPage: NacionalidadComponentsPage;
  let nacionalidadUpdatePage: NacionalidadUpdatePage;
  let nacionalidadDeleteDialog: NacionalidadDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Nacionalidads', async () => {
    await navBarPage.goToEntity('nacionalidad');
    nacionalidadComponentsPage = new NacionalidadComponentsPage();
    await browser.wait(ec.visibilityOf(nacionalidadComponentsPage.title), 5000);
    expect(await nacionalidadComponentsPage.getTitle()).to.eq('testmono04App.nacionalidad.home.title');
  });

  it('should load create Nacionalidad page', async () => {
    await nacionalidadComponentsPage.clickOnCreateButton();
    nacionalidadUpdatePage = new NacionalidadUpdatePage();
    expect(await nacionalidadUpdatePage.getPageTitle()).to.eq('testmono04App.nacionalidad.home.createOrEditLabel');
    await nacionalidadUpdatePage.cancel();
  });

  it('should create and save Nacionalidads', async () => {
    const nbButtonsBeforeCreate = await nacionalidadComponentsPage.countDeleteButtons();

    await nacionalidadComponentsPage.clickOnCreateButton();
    await promise.all([nacionalidadUpdatePage.setNombreInput('nombre')]);
    expect(await nacionalidadUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    const selectedEstado = nacionalidadUpdatePage.getEstadoInput();
    if (await selectedEstado.isSelected()) {
      await nacionalidadUpdatePage.getEstadoInput().click();
      expect(await nacionalidadUpdatePage.getEstadoInput().isSelected(), 'Expected estado not to be selected').to.be.false;
    } else {
      await nacionalidadUpdatePage.getEstadoInput().click();
      expect(await nacionalidadUpdatePage.getEstadoInput().isSelected(), 'Expected estado to be selected').to.be.true;
    }
    await nacionalidadUpdatePage.save();
    expect(await nacionalidadUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await nacionalidadComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Nacionalidad', async () => {
    const nbButtonsBeforeDelete = await nacionalidadComponentsPage.countDeleteButtons();
    await nacionalidadComponentsPage.clickOnLastDeleteButton();

    nacionalidadDeleteDialog = new NacionalidadDeleteDialog();
    expect(await nacionalidadDeleteDialog.getDialogTitle()).to.eq('testmono04App.nacionalidad.delete.question');
    await nacionalidadDeleteDialog.clickOnConfirmButton();

    expect(await nacionalidadComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
