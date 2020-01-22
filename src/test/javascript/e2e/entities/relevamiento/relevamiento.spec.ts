import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RelevamientoComponentsPage, RelevamientoDeleteDialog, RelevamientoUpdatePage } from './relevamiento.page-object';

const expect = chai.expect;

describe('Relevamiento e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let relevamientoComponentsPage: RelevamientoComponentsPage;
  let relevamientoUpdatePage: RelevamientoUpdatePage;
  let relevamientoDeleteDialog: RelevamientoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Relevamientos', async () => {
    await navBarPage.goToEntity('relevamiento');
    relevamientoComponentsPage = new RelevamientoComponentsPage();
    await browser.wait(ec.visibilityOf(relevamientoComponentsPage.title), 5000);
    expect(await relevamientoComponentsPage.getTitle()).to.eq('testmono04App.relevamiento.home.title');
  });

  it('should load create Relevamiento page', async () => {
    await relevamientoComponentsPage.clickOnCreateButton();
    relevamientoUpdatePage = new RelevamientoUpdatePage();
    expect(await relevamientoUpdatePage.getPageTitle()).to.eq('testmono04App.relevamiento.home.createOrEditLabel');
    await relevamientoUpdatePage.cancel();
  });

  it('should create and save Relevamientos', async () => {
    const nbButtonsBeforeCreate = await relevamientoComponentsPage.countDeleteButtons();

    await relevamientoComponentsPage.clickOnCreateButton();
    await promise.all([
      relevamientoUpdatePage.setFechaInput('2000-12-31'),
      relevamientoUpdatePage.integranteSelectLastOption(),
      relevamientoUpdatePage.origenenergiaSelectLastOption(),
      relevamientoUpdatePage.origenaguaSelectLastOption(),
      relevamientoUpdatePage.tiposervicioSelectLastOption()
    ]);
    expect(await relevamientoUpdatePage.getFechaInput()).to.eq('2000-12-31', 'Expected fecha value to be equals to 2000-12-31');
    const selectedEscuela = relevamientoUpdatePage.getEscuelaInput();
    if (await selectedEscuela.isSelected()) {
      await relevamientoUpdatePage.getEscuelaInput().click();
      expect(await relevamientoUpdatePage.getEscuelaInput().isSelected(), 'Expected escuela not to be selected').to.be.false;
    } else {
      await relevamientoUpdatePage.getEscuelaInput().click();
      expect(await relevamientoUpdatePage.getEscuelaInput().isSelected(), 'Expected escuela to be selected').to.be.true;
    }
    const selectedPuestoSalud = relevamientoUpdatePage.getPuestoSaludInput();
    if (await selectedPuestoSalud.isSelected()) {
      await relevamientoUpdatePage.getPuestoSaludInput().click();
      expect(await relevamientoUpdatePage.getPuestoSaludInput().isSelected(), 'Expected puestoSalud not to be selected').to.be.false;
    } else {
      await relevamientoUpdatePage.getPuestoSaludInput().click();
      expect(await relevamientoUpdatePage.getPuestoSaludInput().isSelected(), 'Expected puestoSalud to be selected').to.be.true;
    }
    const selectedEstado = relevamientoUpdatePage.getEstadoInput();
    if (await selectedEstado.isSelected()) {
      await relevamientoUpdatePage.getEstadoInput().click();
      expect(await relevamientoUpdatePage.getEstadoInput().isSelected(), 'Expected estado not to be selected').to.be.false;
    } else {
      await relevamientoUpdatePage.getEstadoInput().click();
      expect(await relevamientoUpdatePage.getEstadoInput().isSelected(), 'Expected estado to be selected').to.be.true;
    }
    await relevamientoUpdatePage.save();
    expect(await relevamientoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await relevamientoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Relevamiento', async () => {
    const nbButtonsBeforeDelete = await relevamientoComponentsPage.countDeleteButtons();
    await relevamientoComponentsPage.clickOnLastDeleteButton();

    relevamientoDeleteDialog = new RelevamientoDeleteDialog();
    expect(await relevamientoDeleteDialog.getDialogTitle()).to.eq('testmono04App.relevamiento.delete.question');
    await relevamientoDeleteDialog.clickOnConfirmButton();

    expect(await relevamientoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
