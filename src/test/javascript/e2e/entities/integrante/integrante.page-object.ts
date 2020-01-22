import { element, by, ElementFinder } from 'protractor';

export class IntegranteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-integrante div table .btn-danger'));
  title = element.all(by.css('jhi-integrante div h2#page-heading span')).first();

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class IntegranteUpdatePage {
  pageTitle = element(by.id('jhi-integrante-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  dniInput = element(by.id('field_dni'));
  apelllidoInput = element(by.id('field_apelllido'));
  nombreInput = element(by.id('field_nombre'));
  fechaNacimientoInput = element(by.id('field_fechaNacimiento'));
  edadInput = element(by.id('field_edad'));
  sexoSelect = element(by.id('field_sexo'));
  estadoInput = element(by.id('field_estado'));
  comunidadSelect = element(by.id('field_comunidad'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDniInput(dni: string): Promise<void> {
    await this.dniInput.sendKeys(dni);
  }

  async getDniInput(): Promise<string> {
    return await this.dniInput.getAttribute('value');
  }

  async setApelllidoInput(apelllido: string): Promise<void> {
    await this.apelllidoInput.sendKeys(apelllido);
  }

  async getApelllidoInput(): Promise<string> {
    return await this.apelllidoInput.getAttribute('value');
  }

  async setNombreInput(nombre: string): Promise<void> {
    await this.nombreInput.sendKeys(nombre);
  }

  async getNombreInput(): Promise<string> {
    return await this.nombreInput.getAttribute('value');
  }

  async setFechaNacimientoInput(fechaNacimiento: string): Promise<void> {
    await this.fechaNacimientoInput.sendKeys(fechaNacimiento);
  }

  async getFechaNacimientoInput(): Promise<string> {
    return await this.fechaNacimientoInput.getAttribute('value');
  }

  async setEdadInput(edad: string): Promise<void> {
    await this.edadInput.sendKeys(edad);
  }

  async getEdadInput(): Promise<string> {
    return await this.edadInput.getAttribute('value');
  }

  async setSexoSelect(sexo: string): Promise<void> {
    await this.sexoSelect.sendKeys(sexo);
  }

  async getSexoSelect(): Promise<string> {
    return await this.sexoSelect.element(by.css('option:checked')).getText();
  }

  async sexoSelectLastOption(): Promise<void> {
    await this.sexoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  getEstadoInput(): ElementFinder {
    return this.estadoInput;
  }

  async comunidadSelectLastOption(): Promise<void> {
    await this.comunidadSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async comunidadSelectOption(option: string): Promise<void> {
    await this.comunidadSelect.sendKeys(option);
  }

  getComunidadSelect(): ElementFinder {
    return this.comunidadSelect;
  }

  async getComunidadSelectedOption(): Promise<string> {
    return await this.comunidadSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class IntegranteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-integrante-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-integrante'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
