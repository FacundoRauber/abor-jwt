import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IntegranteComponentsPage, IntegranteDeleteDialog, IntegranteUpdatePage } from './integrante.page-object';

const expect = chai.expect;

describe('Integrante e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let integranteComponentsPage: IntegranteComponentsPage;
  let integranteUpdatePage: IntegranteUpdatePage;
  let integranteDeleteDialog: IntegranteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Integrantes', async () => {
    await navBarPage.goToEntity('integrante');
    integranteComponentsPage = new IntegranteComponentsPage();
    await browser.wait(ec.visibilityOf(integranteComponentsPage.title), 5000);
    expect(await integranteComponentsPage.getTitle()).to.eq('testmono04App.integrante.home.title');
  });

  it('should load create Integrante page', async () => {
    await integranteComponentsPage.clickOnCreateButton();
    integranteUpdatePage = new IntegranteUpdatePage();
    expect(await integranteUpdatePage.getPageTitle()).to.eq('testmono04App.integrante.home.createOrEditLabel');
    await integranteUpdatePage.cancel();
  });

  it('should create and save Integrantes', async () => {
    const nbButtonsBeforeCreate = await integranteComponentsPage.countDeleteButtons();

    await integranteComponentsPage.clickOnCreateButton();
    await promise.all([
      integranteUpdatePage.setDniInput('5'),
      integranteUpdatePage.setApelllidoInput('apelllido'),
      integranteUpdatePage.setNombreInput('nombre'),
      integranteUpdatePage.setFechaNacimientoInput('2000-12-31'),
      integranteUpdatePage.setEdadInput('5'),
      integranteUpdatePage.sexoSelectLastOption(),
      integranteUpdatePage.comunidadSelectLastOption()
    ]);
    expect(await integranteUpdatePage.getDniInput()).to.eq('5', 'Expected dni value to be equals to 5');
    expect(await integranteUpdatePage.getApelllidoInput()).to.eq('apelllido', 'Expected Apelllido value to be equals to apelllido');
    expect(await integranteUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    expect(await integranteUpdatePage.getFechaNacimientoInput()).to.eq(
      '2000-12-31',
      'Expected fechaNacimiento value to be equals to 2000-12-31'
    );
    expect(await integranteUpdatePage.getEdadInput()).to.eq('5', 'Expected edad value to be equals to 5');
    const selectedEstado = integranteUpdatePage.getEstadoInput();
    if (await selectedEstado.isSelected()) {
      await integranteUpdatePage.getEstadoInput().click();
      expect(await integranteUpdatePage.getEstadoInput().isSelected(), 'Expected estado not to be selected').to.be.false;
    } else {
      await integranteUpdatePage.getEstadoInput().click();
      expect(await integranteUpdatePage.getEstadoInput().isSelected(), 'Expected estado to be selected').to.be.true;
    }
    await integranteUpdatePage.save();
    expect(await integranteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await integranteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Integrante', async () => {
    const nbButtonsBeforeDelete = await integranteComponentsPage.countDeleteButtons();
    await integranteComponentsPage.clickOnLastDeleteButton();

    integranteDeleteDialog = new IntegranteDeleteDialog();
    expect(await integranteDeleteDialog.getDialogTitle()).to.eq('testmono04App.integrante.delete.question');
    await integranteDeleteDialog.clickOnConfirmButton();

    expect(await integranteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
