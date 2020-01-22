import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TipoServicioComponentsPage, TipoServicioDeleteDialog, TipoServicioUpdatePage } from './tipo-servicio.page-object';

const expect = chai.expect;

describe('TipoServicio e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tipoServicioComponentsPage: TipoServicioComponentsPage;
  let tipoServicioUpdatePage: TipoServicioUpdatePage;
  let tipoServicioDeleteDialog: TipoServicioDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TipoServicios', async () => {
    await navBarPage.goToEntity('tipo-servicio');
    tipoServicioComponentsPage = new TipoServicioComponentsPage();
    await browser.wait(ec.visibilityOf(tipoServicioComponentsPage.title), 5000);
    expect(await tipoServicioComponentsPage.getTitle()).to.eq('testmono04App.tipoServicio.home.title');
  });

  it('should load create TipoServicio page', async () => {
    await tipoServicioComponentsPage.clickOnCreateButton();
    tipoServicioUpdatePage = new TipoServicioUpdatePage();
    expect(await tipoServicioUpdatePage.getPageTitle()).to.eq('testmono04App.tipoServicio.home.createOrEditLabel');
    await tipoServicioUpdatePage.cancel();
  });

  it('should create and save TipoServicios', async () => {
    const nbButtonsBeforeCreate = await tipoServicioComponentsPage.countDeleteButtons();

    await tipoServicioComponentsPage.clickOnCreateButton();
    await promise.all([tipoServicioUpdatePage.setNombreInput('nombre')]);
    expect(await tipoServicioUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    const selectedEstado = tipoServicioUpdatePage.getEstadoInput();
    if (await selectedEstado.isSelected()) {
      await tipoServicioUpdatePage.getEstadoInput().click();
      expect(await tipoServicioUpdatePage.getEstadoInput().isSelected(), 'Expected estado not to be selected').to.be.false;
    } else {
      await tipoServicioUpdatePage.getEstadoInput().click();
      expect(await tipoServicioUpdatePage.getEstadoInput().isSelected(), 'Expected estado to be selected').to.be.true;
    }
    await tipoServicioUpdatePage.save();
    expect(await tipoServicioUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tipoServicioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TipoServicio', async () => {
    const nbButtonsBeforeDelete = await tipoServicioComponentsPage.countDeleteButtons();
    await tipoServicioComponentsPage.clickOnLastDeleteButton();

    tipoServicioDeleteDialog = new TipoServicioDeleteDialog();
    expect(await tipoServicioDeleteDialog.getDialogTitle()).to.eq('testmono04App.tipoServicio.delete.question');
    await tipoServicioDeleteDialog.clickOnConfirmButton();

    expect(await tipoServicioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
