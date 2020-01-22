import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRelevamiento } from 'app/shared/model/relevamiento.model';
import { RelevamientoService } from './relevamiento.service';

@Component({
  templateUrl: './relevamiento-delete-dialog.component.html'
})
export class RelevamientoDeleteDialogComponent {
  relevamiento?: IRelevamiento;

  constructor(
    protected relevamientoService: RelevamientoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.relevamientoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('relevamientoListModification');
      this.activeModal.close();
    });
  }
}
