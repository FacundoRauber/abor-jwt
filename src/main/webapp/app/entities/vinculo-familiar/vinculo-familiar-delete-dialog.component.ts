import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVinculoFamiliar } from 'app/shared/model/vinculo-familiar.model';
import { VinculoFamiliarService } from './vinculo-familiar.service';

@Component({
  templateUrl: './vinculo-familiar-delete-dialog.component.html'
})
export class VinculoFamiliarDeleteDialogComponent {
  vinculoFamiliar?: IVinculoFamiliar;

  constructor(
    protected vinculoFamiliarService: VinculoFamiliarService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vinculoFamiliarService.delete(id).subscribe(() => {
      this.eventManager.broadcast('vinculoFamiliarListModification');
      this.activeModal.close();
    });
  }
}
