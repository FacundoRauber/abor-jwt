import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoServicio } from 'app/shared/model/tipo-servicio.model';
import { TipoServicioService } from './tipo-servicio.service';

@Component({
  templateUrl: './tipo-servicio-delete-dialog.component.html'
})
export class TipoServicioDeleteDialogComponent {
  tipoServicio?: ITipoServicio;

  constructor(
    protected tipoServicioService: TipoServicioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoServicioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoServicioListModification');
      this.activeModal.close();
    });
  }
}
