import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIntegrante } from 'app/shared/model/integrante.model';
import { IntegranteService } from './integrante.service';

@Component({
  templateUrl: './integrante-delete-dialog.component.html'
})
export class IntegranteDeleteDialogComponent {
  integrante?: IIntegrante;

  constructor(
    protected integranteService: IntegranteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.integranteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('integranteListModification');
      this.activeModal.close();
    });
  }
}
