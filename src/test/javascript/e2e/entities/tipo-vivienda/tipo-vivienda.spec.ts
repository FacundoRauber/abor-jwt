import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TipoViviendaComponentsPage, TipoViviendaDeleteDialog, TipoViviendaUpdatePage } from './tipo-vivienda.page-object';

const expect = chai.expect;

describe('TipoVivienda e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tipoViviendaComponentsPage: TipoViviendaComponentsPage;
  let tipoViviendaUpdatePage: TipoViviendaUpdatePage;
  let tipoViviendaDeleteDialog: TipoViviendaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TipoViviendas', async () => {
    await navBarPage.goToEntity('tipo-vivienda');
    tipoViviendaComponentsPage = new TipoViviendaComponentsPage();
    await browser.wait(ec.visibilityOf(tipoViviendaComponentsPage.title), 5000);
    expect(await tipoViviendaComponentsPage.getTitle()).to.eq('testmono04App.tipoVivienda.home.title');
  });

  it('should load create TipoVivienda page', async () => {
    await tipoViviendaComponentsPage.clickOnCreateButton();
    tipoViviendaUpdatePage = new TipoViviendaUpdatePage();
    expect(await tipoViviendaUpdatePage.getPageTitle()).to.eq('testmono04App.tipoVivienda.home.createOrEditLabel');
    await tipoViviendaUpdatePage.cancel();
  });

  it('should create and save TipoViviendas', async () => {
    const nbButtonsBeforeCreate = await tipoViviendaComponentsPage.countDeleteButtons();

    await tipoViviendaComponentsPage.clickOnCreateButton();
    await promise.all([tipoViviendaUpdatePage.setNombreInput('nombre')]);
    expect(await tipoViviendaUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    const selectedEstado = tipoViviendaUpdatePage.getEstadoInput();
    if (await selectedEstado.isSelected()) {
      await tipoViviendaUpdatePage.getEstadoInput().click();
      expect(await tipoViviendaUpdatePage.getEstadoInput().isSelected(), 'Expected estado not to be selected').to.be.false;
    } else {
      await tipoViviendaUpdatePage.getEstadoInput().click();
      expect(await tipoViviendaUpdatePage.getEstadoInput().isSelected(), 'Expected estado to be selected').to.be.true;
    }
    await tipoViviendaUpdatePage.save();
    expect(await tipoViviendaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tipoViviendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TipoVivienda', async () => {
    const nbButtonsBeforeDelete = await tipoViviendaComponentsPage.countDeleteButtons();
    await tipoViviendaComponentsPage.clickOnLastDeleteButton();

    tipoViviendaDeleteDialog = new TipoViviendaDeleteDialog();
    expect(await tipoViviendaDeleteDialog.getDialogTitle()).to.eq('testmono04App.tipoVivienda.delete.question');
    await tipoViviendaDeleteDialog.clickOnConfirmButton();

    expect(await tipoViviendaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
