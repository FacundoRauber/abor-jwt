import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TipoTratamientoBasuraComponentsPage,
  TipoTratamientoBasuraDeleteDialog,
  TipoTratamientoBasuraUpdatePage
} from './tipo-tratamiento-basura.page-object';

const expect = chai.expect;

describe('TipoTratamientoBasura e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tipoTratamientoBasuraComponentsPage: TipoTratamientoBasuraComponentsPage;
  let tipoTratamientoBasuraUpdatePage: TipoTratamientoBasuraUpdatePage;
  let tipoTratamientoBasuraDeleteDialog: TipoTratamientoBasuraDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TipoTratamientoBasuras', async () => {
    await navBarPage.goToEntity('tipo-tratamiento-basura');
    tipoTratamientoBasuraComponentsPage = new TipoTratamientoBasuraComponentsPage();
    await browser.wait(ec.visibilityOf(tipoTratamientoBasuraComponentsPage.title), 5000);
    expect(await tipoTratamientoBasuraComponentsPage.getTitle()).to.eq('testmono04App.tipoTratamientoBasura.home.title');
  });

  it('should load create TipoTratamientoBasura page', async () => {
    await tipoTratamientoBasuraComponentsPage.clickOnCreateButton();
    tipoTratamientoBasuraUpdatePage = new TipoTratamientoBasuraUpdatePage();
    expect(await tipoTratamientoBasuraUpdatePage.getPageTitle()).to.eq('testmono04App.tipoTratamientoBasura.home.createOrEditLabel');
    await tipoTratamientoBasuraUpdatePage.cancel();
  });

  it('should create and save TipoTratamientoBasuras', async () => {
    const nbButtonsBeforeCreate = await tipoTratamientoBasuraComponentsPage.countDeleteButtons();

    await tipoTratamientoBasuraComponentsPage.clickOnCreateButton();
    await promise.all([tipoTratamientoBasuraUpdatePage.setNombreInput('nombre')]);
    expect(await tipoTratamientoBasuraUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    const selectedEstado = tipoTratamientoBasuraUpdatePage.getEstadoInput();
    if (await selectedEstado.isSelected()) {
      await tipoTratamientoBasuraUpdatePage.getEstadoInput().click();
      expect(await tipoTratamientoBasuraUpdatePage.getEstadoInput().isSelected(), 'Expected estado not to be selected').to.be.false;
    } else {
      await tipoTratamientoBasuraUpdatePage.getEstadoInput().click();
      expect(await tipoTratamientoBasuraUpdatePage.getEstadoInput().isSelected(), 'Expected estado to be selected').to.be.true;
    }
    await tipoTratamientoBasuraUpdatePage.save();
    expect(await tipoTratamientoBasuraUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tipoTratamientoBasuraComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TipoTratamientoBasura', async () => {
    const nbButtonsBeforeDelete = await tipoTratamientoBasuraComponentsPage.countDeleteButtons();
    await tipoTratamientoBasuraComponentsPage.clickOnLastDeleteButton();

    tipoTratamientoBasuraDeleteDialog = new TipoTratamientoBasuraDeleteDialog();
    expect(await tipoTratamientoBasuraDeleteDialog.getDialogTitle()).to.eq('testmono04App.tipoTratamientoBasura.delete.question');
    await tipoTratamientoBasuraDeleteDialog.clickOnConfirmButton();

    expect(await tipoTratamientoBasuraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
