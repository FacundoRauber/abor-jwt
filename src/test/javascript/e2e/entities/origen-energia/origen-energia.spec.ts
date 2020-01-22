import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrigenEnergiaComponentsPage, OrigenEnergiaDeleteDialog, OrigenEnergiaUpdatePage } from './origen-energia.page-object';

const expect = chai.expect;

describe('OrigenEnergia e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let origenEnergiaComponentsPage: OrigenEnergiaComponentsPage;
  let origenEnergiaUpdatePage: OrigenEnergiaUpdatePage;
  let origenEnergiaDeleteDialog: OrigenEnergiaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OrigenEnergias', async () => {
    await navBarPage.goToEntity('origen-energia');
    origenEnergiaComponentsPage = new OrigenEnergiaComponentsPage();
    await browser.wait(ec.visibilityOf(origenEnergiaComponentsPage.title), 5000);
    expect(await origenEnergiaComponentsPage.getTitle()).to.eq('testmono04App.origenEnergia.home.title');
  });

  it('should load create OrigenEnergia page', async () => {
    await origenEnergiaComponentsPage.clickOnCreateButton();
    origenEnergiaUpdatePage = new OrigenEnergiaUpdatePage();
    expect(await origenEnergiaUpdatePage.getPageTitle()).to.eq('testmono04App.origenEnergia.home.createOrEditLabel');
    await origenEnergiaUpdatePage.cancel();
  });

  it('should create and save OrigenEnergias', async () => {
    const nbButtonsBeforeCreate = await origenEnergiaComponentsPage.countDeleteButtons();

    await origenEnergiaComponentsPage.clickOnCreateButton();
    await promise.all([origenEnergiaUpdatePage.setNombreInput('nombre')]);
    expect(await origenEnergiaUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    const selectedEstado = origenEnergiaUpdatePage.getEstadoInput();
    if (await selectedEstado.isSelected()) {
      await origenEnergiaUpdatePage.getEstadoInput().click();
      expect(await origenEnergiaUpdatePage.getEstadoInput().isSelected(), 'Expected estado not to be selected').to.be.false;
    } else {
      await origenEnergiaUpdatePage.getEstadoInput().click();
      expect(await origenEnergiaUpdatePage.getEstadoInput().isSelected(), 'Expected estado to be selected').to.be.true;
    }
    await origenEnergiaUpdatePage.save();
    expect(await origenEnergiaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await origenEnergiaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last OrigenEnergia', async () => {
    const nbButtonsBeforeDelete = await origenEnergiaComponentsPage.countDeleteButtons();
    await origenEnergiaComponentsPage.clickOnLastDeleteButton();

    origenEnergiaDeleteDialog = new OrigenEnergiaDeleteDialog();
    expect(await origenEnergiaDeleteDialog.getDialogTitle()).to.eq('testmono04App.origenEnergia.delete.question');
    await origenEnergiaDeleteDialog.clickOnConfirmButton();

    expect(await origenEnergiaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
