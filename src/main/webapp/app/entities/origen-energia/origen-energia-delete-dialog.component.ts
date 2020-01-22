import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrigenEnergia } from 'app/shared/model/origen-energia.model';
import { OrigenEnergiaService } from './origen-energia.service';

@Component({
  templateUrl: './origen-energia-delete-dialog.component.html'
})
export class OrigenEnergiaDeleteDialogComponent {
  origenEnergia?: IOrigenEnergia;

  constructor(
    protected origenEnergiaService: OrigenEnergiaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.origenEnergiaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('origenEnergiaListModification');
      this.activeModal.close();
    });
  }
}
