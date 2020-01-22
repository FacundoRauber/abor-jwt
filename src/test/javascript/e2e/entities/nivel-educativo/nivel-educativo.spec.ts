import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { NivelEducativoComponentsPage, NivelEducativoDeleteDialog, NivelEducativoUpdatePage } from './nivel-educativo.page-object';

const expect = chai.expect;

describe('NivelEducativo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let nivelEducativoComponentsPage: NivelEducativoComponentsPage;
  let nivelEducativoUpdatePage: NivelEducativoUpdatePage;
  let nivelEducativoDeleteDialog: NivelEducativoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load NivelEducativos', async () => {
    await navBarPage.goToEntity('nivel-educativo');
    nivelEducativoComponentsPage = new NivelEducativoComponentsPage();
    await browser.wait(ec.visibilityOf(nivelEducativoComponentsPage.title), 5000);
    expect(await nivelEducativoComponentsPage.getTitle()).to.eq('testmono04App.nivelEducativo.home.title');
  });

  it('should load create NivelEducativo page', async () => {
    await nivelEducativoComponentsPage.clickOnCreateButton();
    nivelEducativoUpdatePage = new NivelEducativoUpdatePage();
    expect(await nivelEducativoUpdatePage.getPageTitle()).to.eq('testmono04App.nivelEducativo.home.createOrEditLabel');
    await nivelEducativoUpdatePage.cancel();
  });

  it('should create and save NivelEducativos', async () => {
    const nbButtonsBeforeCreate = await nivelEducativoComponentsPage.countDeleteButtons();

    await nivelEducativoComponentsPage.clickOnCreateButton();
    await promise.all([nivelEducativoUpdatePage.setNombreInput('nombre')]);
    expect(await nivelEducativoUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    const selectedEstado = nivelEducativoUpdatePage.getEstadoInput();
    if (await selectedEstado.isSelected()) {
      await nivelEducativoUpdatePage.getEstadoInput().click();
      expect(await nivelEducativoUpdatePage.getEstadoInput().isSelected(), 'Expected estado not to be selected').to.be.false;
    } else {
      await nivelEducativoUpdatePage.getEstadoInput().click();
      expect(await nivelEducativoUpdatePage.getEstadoInput().isSelected(), 'Expected estado to be selected').to.be.true;
    }
    await nivelEducativoUpdatePage.save();
    expect(await nivelEducativoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await nivelEducativoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last NivelEducativo', async () => {
    const nbButtonsBeforeDelete = await nivelEducativoComponentsPage.countDeleteButtons();
    await nivelEducativoComponentsPage.clickOnLastDeleteButton();

    nivelEducativoDeleteDialog = new NivelEducativoDeleteDialog();
    expect(await nivelEducativoDeleteDialog.getDialogTitle()).to.eq('testmono04App.nivelEducativo.delete.question');
    await nivelEducativoDeleteDialog.clickOnConfirmButton();

    expect(await nivelEducativoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
