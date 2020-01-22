import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoOcupacion } from 'app/shared/model/tipo-ocupacion.model';
import { TipoOcupacionService } from './tipo-ocupacion.service';

@Component({
  templateUrl: './tipo-ocupacion-delete-dialog.component.html'
})
export class TipoOcupacionDeleteDialogComponent {
  tipoOcupacion?: ITipoOcupacion;

  constructor(
    protected tipoOcupacionService: TipoOcupacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoOcupacionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoOcupacionListModification');
      this.activeModal.close();
    });
  }
}
