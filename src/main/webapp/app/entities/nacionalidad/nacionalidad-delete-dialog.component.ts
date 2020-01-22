import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INacionalidad } from 'app/shared/model/nacionalidad.model';
import { NacionalidadService } from './nacionalidad.service';

@Component({
  templateUrl: './nacionalidad-delete-dialog.component.html'
})
export class NacionalidadDeleteDialogComponent {
  nacionalidad?: INacionalidad;

  constructor(
    protected nacionalidadService: NacionalidadService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nacionalidadService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nacionalidadListModification');
      this.activeModal.close();
    });
  }
}
