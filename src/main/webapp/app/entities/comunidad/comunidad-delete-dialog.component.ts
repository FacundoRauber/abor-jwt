import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComunidad } from 'app/shared/model/comunidad.model';
import { ComunidadService } from './comunidad.service';

@Component({
  templateUrl: './comunidad-delete-dialog.component.html'
})
export class ComunidadDeleteDialogComponent {
  comunidad?: IComunidad;

  constructor(protected comunidadService: ComunidadService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.comunidadService.delete(id).subscribe(() => {
      this.eventManager.broadcast('comunidadListModification');
      this.activeModal.close();
    });
  }
}
