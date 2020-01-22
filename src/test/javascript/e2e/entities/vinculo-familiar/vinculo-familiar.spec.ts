import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VinculoFamiliarComponentsPage, VinculoFamiliarDeleteDialog, VinculoFamiliarUpdatePage } from './vinculo-familiar.page-object';

const expect = chai.expect;

describe('VinculoFamiliar e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let vinculoFamiliarComponentsPage: VinculoFamiliarComponentsPage;
  let vinculoFamiliarUpdatePage: VinculoFamiliarUpdatePage;
  let vinculoFamiliarDeleteDialog: VinculoFamiliarDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load VinculoFamiliars', async () => {
    await navBarPage.goToEntity('vinculo-familiar');
    vinculoFamiliarComponentsPage = new VinculoFamiliarComponentsPage();
    await browser.wait(ec.visibilityOf(vinculoFamiliarComponentsPage.title), 5000);
    expect(await vinculoFamiliarComponentsPage.getTitle()).to.eq('testmono04App.vinculoFamiliar.home.title');
  });

  it('should load create VinculoFamiliar page', async () => {
    await vinculoFamiliarComponentsPage.clickOnCreateButton();
    vinculoFamiliarUpdatePage = new VinculoFamiliarUpdatePage();
    expect(await vinculoFamiliarUpdatePage.getPageTitle()).to.eq('testmono04App.vinculoFamiliar.home.createOrEditLabel');
    await vinculoFamiliarUpdatePage.cancel();
  });

  it('should create and save VinculoFamiliars', async () => {
    const nbButtonsBeforeCreate = await vinculoFamiliarComponentsPage.countDeleteButtons();

    await vinculoFamiliarComponentsPage.clickOnCreateButton();
    await promise.all([vinculoFamiliarUpdatePage.setNombreInput('nombre')]);
    expect(await vinculoFamiliarUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    const selectedEstado = vinculoFamiliarUpdatePage.getEstadoInput();
    if (await selectedEstado.isSelected()) {
      await vinculoFamiliarUpdatePage.getEstadoInput().click();
      expect(await vinculoFamiliarUpdatePage.getEstadoInput().isSelected(), 'Expected estado not to be selected').to.be.false;
    } else {
      await vinculoFamiliarUpdatePage.getEstadoInput().click();
      expect(await vinculoFamiliarUpdatePage.getEstadoInput().isSelected(), 'Expected estado to be selected').to.be.true;
    }
    await vinculoFamiliarUpdatePage.save();
    expect(await vinculoFamiliarUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await vinculoFamiliarComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last VinculoFamiliar', async () => {
    const nbButtonsBeforeDelete = await vinculoFamiliarComponentsPage.countDeleteButtons();
    await vinculoFamiliarComponentsPage.clickOnLastDeleteButton();

    vinculoFamiliarDeleteDialog = new VinculoFamiliarDeleteDialog();
    expect(await vinculoFamiliarDeleteDialog.getDialogTitle()).to.eq('testmono04App.vinculoFamiliar.delete.question');
    await vinculoFamiliarDeleteDialog.clickOnConfirmButton();

    expect(await vinculoFamiliarComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
