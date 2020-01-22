import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoPlanAsistencia } from 'app/shared/model/tipo-plan-asistencia.model';
import { TipoPlanAsistenciaService } from './tipo-plan-asistencia.service';

@Component({
  templateUrl: './tipo-plan-asistencia-delete-dialog.component.html'
})
export class TipoPlanAsistenciaDeleteDialogComponent {
  tipoPlanAsistencia?: ITipoPlanAsistencia;

  constructor(
    protected tipoPlanAsistenciaService: TipoPlanAsistenciaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoPlanAsistenciaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoPlanAsistenciaListModification');
      this.activeModal.close();
    });
  }
}
