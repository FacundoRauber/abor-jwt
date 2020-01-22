import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoTratamientoBasura } from 'app/shared/model/tipo-tratamiento-basura.model';
import { TipoTratamientoBasuraService } from './tipo-tratamiento-basura.service';

@Component({
  templateUrl: './tipo-tratamiento-basura-delete-dialog.component.html'
})
export class TipoTratamientoBasuraDeleteDialogComponent {
  tipoTratamientoBasura?: ITipoTratamientoBasura;

  constructor(
    protected tipoTratamientoBasuraService: TipoTratamientoBasuraService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoTratamientoBasuraService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoTratamientoBasuraListModification');
      this.activeModal.close();
    });
  }
}
