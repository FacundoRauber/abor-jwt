import { element, by, ElementFinder } from 'protractor';

export class RelevamientoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-relevamiento div table .btn-danger'));
  title = element.all(by.css('jhi-relevamiento div h2#page-heading span')).first();

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

export class RelevamientoUpdatePage {
  pageTitle = element(by.id('jhi-relevamiento-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  fechaInput = element(by.id('field_fecha'));
  escuelaInput = element(by.id('field_escuela'));
  puestoSaludInput = element(by.id('field_puestoSalud'));
  estadoInput = element(by.id('field_estado'));
  integranteSelect = element(by.id('field_integrante'));
  origenenergiaSelect = element(by.id('field_origenenergia'));
  origenaguaSelect = element(by.id('field_origenagua'));
  tiposervicioSelect = element(by.id('field_tiposervicio'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFechaInput(fecha: string): Promise<void> {
    await this.fechaInput.sendKeys(fecha);
  }

  async getFechaInput(): Promise<string> {
    return await this.fechaInput.getAttribute('value');
  }

  getEscuelaInput(): ElementFinder {
    return this.escuelaInput;
  }
  getPuestoSaludInput(): ElementFinder {
    return this.puestoSaludInput;
  }
  getEstadoInput(): ElementFinder {
    return this.estadoInput;
  }

  async integranteSelectLastOption(): Promise<void> {
    await this.integranteSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async integranteSelectOption(option: string): Promise<void> {
    await this.integranteSelect.sendKeys(option);
  }

  getIntegranteSelect(): ElementFinder {
    return this.integranteSelect;
  }

  async getIntegranteSelectedOption(): Promise<string> {
    return await this.integranteSelect.element(by.css('option:checked')).getText();
  }

  async origenenergiaSelectLastOption(): Promise<void> {
    await this.origenenergiaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async origenenergiaSelectOption(option: string): Promise<void> {
    await this.origenenergiaSelect.sendKeys(option);
  }

  getOrigenenergiaSelect(): ElementFinder {
    return this.origenenergiaSelect;
  }

  async getOrigenenergiaSelectedOption(): Promise<string> {
    return await this.origenenergiaSelect.element(by.css('option:checked')).getText();
  }

  async origenaguaSelectLastOption(): Promise<void> {
    await this.origenaguaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async origenaguaSelectOption(option: string): Promise<void> {
    await this.origenaguaSelect.sendKeys(option);
  }

  getOrigenaguaSelect(): ElementFinder {
    return this.origenaguaSelect;
  }

  async getOrigenaguaSelectedOption(): Promise<string> {
    return await this.origenaguaSelect.element(by.css('option:checked')).getText();
  }

  async tiposervicioSelectLastOption(): Promise<void> {
    await this.tiposervicioSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async tiposervicioSelectOption(option: string): Promise<void> {
    await this.tiposervicioSelect.sendKeys(option);
  }

  getTiposervicioSelect(): ElementFinder {
    return this.tiposervicioSelect;
  }

  async getTiposervicioSelectedOption(): Promise<string> {
    return await this.tiposervicioSelect.element(by.css('option:checked')).getText();
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

export class RelevamientoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-relevamiento-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-relevamiento'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
