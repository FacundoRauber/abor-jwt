import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrigenAgua } from 'app/shared/model/origen-agua.model';
import { OrigenAguaService } from './origen-agua.service';

@Component({
  templateUrl: './origen-agua-delete-dialog.component.html'
})
export class OrigenAguaDeleteDialogComponent {
  origenAgua?: IOrigenAgua;

  constructor(
    protected origenAguaService: OrigenAguaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.origenAguaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('origenAguaListModification');
      this.activeModal.close();
    });
  }
}
