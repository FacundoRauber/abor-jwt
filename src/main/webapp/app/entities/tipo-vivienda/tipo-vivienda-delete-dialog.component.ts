import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoVivienda } from 'app/shared/model/tipo-vivienda.model';
import { TipoViviendaService } from './tipo-vivienda.service';

@Component({
  templateUrl: './tipo-vivienda-delete-dialog.component.html'
})
export class TipoViviendaDeleteDialogComponent {
  tipoVivienda?: ITipoVivienda;

  constructor(
    protected tipoViviendaService: TipoViviendaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoViviendaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoViviendaListModification');
      this.activeModal.close();
    });
  }
}
