import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INivelEducativo } from 'app/shared/model/nivel-educativo.model';
import { NivelEducativoService } from './nivel-educativo.service';

@Component({
  templateUrl: './nivel-educativo-delete-dialog.component.html'
})
export class NivelEducativoDeleteDialogComponent {
  nivelEducativo?: INivelEducativo;

  constructor(
    protected nivelEducativoService: NivelEducativoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nivelEducativoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nivelEducativoListModification');
      this.activeModal.close();
    });
  }
}
