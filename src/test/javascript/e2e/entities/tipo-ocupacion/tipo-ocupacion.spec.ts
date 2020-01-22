import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TipoOcupacionComponentsPage, TipoOcupacionDeleteDialog, TipoOcupacionUpdatePage } from './tipo-ocupacion.page-object';

const expect = chai.expect;

describe('TipoOcupacion e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tipoOcupacionComponentsPage: TipoOcupacionComponentsPage;
  let tipoOcupacionUpdatePage: TipoOcupacionUpdatePage;
  let tipoOcupacionDeleteDialog: TipoOcupacionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TipoOcupacions', async () => {
    await navBarPage.goToEntity('tipo-ocupacion');
    tipoOcupacionComponentsPage = new TipoOcupacionComponentsPage();
    await browser.wait(ec.visibilityOf(tipoOcupacionComponentsPage.title), 5000);
    expect(await tipoOcupacionComponentsPage.getTitle()).to.eq('testmono04App.tipoOcupacion.home.title');
  });

  it('should load create TipoOcupacion page', async () => {
    await tipoOcupacionComponentsPage.clickOnCreateButton();
    tipoOcupacionUpdatePage = new TipoOcupacionUpdatePage();
    expect(await tipoOcupacionUpdatePage.getPageTitle()).to.eq('testmono04App.tipoOcupacion.home.createOrEditLabel');
    await tipoOcupacionUpdatePage.cancel();
  });

  it('should create and save TipoOcupacions', async () => {
    const nbButtonsBeforeCreate = await tipoOcupacionComponentsPage.countDeleteButtons();

    await tipoOcupacionComponentsPage.clickOnCreateButton();
    await promise.all([tipoOcupacionUpdatePage.setNombreInput('nombre')]);
    expect(await tipoOcupacionUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    const selectedEstado = tipoOcupacionUpdatePage.getEstadoInput();
    if (await selectedEstado.isSelected()) {
      await tipoOcupacionUpdatePage.getEstadoInput().click();
      expect(await tipoOcupacionUpdatePage.getEstadoInput().isSelected(), 'Expected estado not to be selected').to.be.false;
    } else {
      await tipoOcupacionUpdatePage.getEstadoInput().click();
      expect(await tipoOcupacionUpdatePage.getEstadoInput().isSelected(), 'Expected estado to be selected').to.be.true;
    }
    await tipoOcupacionUpdatePage.save();
    expect(await tipoOcupacionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tipoOcupacionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TipoOcupacion', async () => {
    const nbButtonsBeforeDelete = await tipoOcupacionComponentsPage.countDeleteButtons();
    await tipoOcupacionComponentsPage.clickOnLastDeleteButton();

    tipoOcupacionDeleteDialog = new TipoOcupacionDeleteDialog();
    expect(await tipoOcupacionDeleteDialog.getDialogTitle()).to.eq('testmono04App.tipoOcupacion.delete.question');
    await tipoOcupacionDeleteDialog.clickOnConfirmButton();

    expect(await tipoOcupacionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
