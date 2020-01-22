import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TipoPlanAsistenciaComponentsPage,
  TipoPlanAsistenciaDeleteDialog,
  TipoPlanAsistenciaUpdatePage
} from './tipo-plan-asistencia.page-object';

const expect = chai.expect;

describe('TipoPlanAsistencia e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tipoPlanAsistenciaComponentsPage: TipoPlanAsistenciaComponentsPage;
  let tipoPlanAsistenciaUpdatePage: TipoPlanAsistenciaUpdatePage;
  let tipoPlanAsistenciaDeleteDialog: TipoPlanAsistenciaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TipoPlanAsistencias', async () => {
    await navBarPage.goToEntity('tipo-plan-asistencia');
    tipoPlanAsistenciaComponentsPage = new TipoPlanAsistenciaComponentsPage();
    await browser.wait(ec.visibilityOf(tipoPlanAsistenciaComponentsPage.title), 5000);
    expect(await tipoPlanAsistenciaComponentsPage.getTitle()).to.eq('testmono04App.tipoPlanAsistencia.home.title');
  });

  it('should load create TipoPlanAsistencia page', async () => {
    await tipoPlanAsistenciaComponentsPage.clickOnCreateButton();
    tipoPlanAsistenciaUpdatePage = new TipoPlanAsistenciaUpdatePage();
    expect(await tipoPlanAsistenciaUpdatePage.getPageTitle()).to.eq('testmono04App.tipoPlanAsistencia.home.createOrEditLabel');
    await tipoPlanAsistenciaUpdatePage.cancel();
  });

  it('should create and save TipoPlanAsistencias', async () => {
    const nbButtonsBeforeCreate = await tipoPlanAsistenciaComponentsPage.countDeleteButtons();

    await tipoPlanAsistenciaComponentsPage.clickOnCreateButton();
    await promise.all([tipoPlanAsistenciaUpdatePage.setNombreInput('nombre')]);
    expect(await tipoPlanAsistenciaUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    const selectedEstado = tipoPlanAsistenciaUpdatePage.getEstadoInput();
    if (await selectedEstado.isSelected()) {
      await tipoPlanAsistenciaUpdatePage.getEstadoInput().click();
      expect(await tipoPlanAsistenciaUpdatePage.getEstadoInput().isSelected(), 'Expected estado not to be selected').to.be.false;
    } else {
      await tipoPlanAsistenciaUpdatePage.getEstadoInput().click();
      expect(await tipoPlanAsistenciaUpdatePage.getEstadoInput().isSelected(), 'Expected estado to be selected').to.be.true;
    }
    await tipoPlanAsistenciaUpdatePage.save();
    expect(await tipoPlanAsistenciaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tipoPlanAsistenciaComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TipoPlanAsistencia', async () => {
    const nbButtonsBeforeDelete = await tipoPlanAsistenciaComponentsPage.countDeleteButtons();
    await tipoPlanAsistenciaComponentsPage.clickOnLastDeleteButton();

    tipoPlanAsistenciaDeleteDialog = new TipoPlanAsistenciaDeleteDialog();
    expect(await tipoPlanAsistenciaDeleteDialog.getDialogTitle()).to.eq('testmono04App.tipoPlanAsistencia.delete.question');
    await tipoPlanAsistenciaDeleteDialog.clickOnConfirmButton();

    expect(await tipoPlanAsistenciaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
