import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoPlanAsistencia } from 'app/shared/model/tipo-plan-asistencia.model';
import { TipoPlanAsistenciaService } from './tipo-plan-asistencia.service';
import { TipoPlanAsistenciaDeleteDialogComponent } from './tipo-plan-asistencia-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-plan-asistencia',
  templateUrl: './tipo-plan-asistencia.component.html'
})
export class TipoPlanAsistenciaComponent implements OnInit, OnDestroy {
  tipoPlanAsistencias?: ITipoPlanAsistencia[];
  eventSubscriber?: Subscription;

  constructor(
    protected tipoPlanAsistenciaService: TipoPlanAsistenciaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tipoPlanAsistenciaService.query().subscribe((res: HttpResponse<ITipoPlanAsistencia[]>) => {
      this.tipoPlanAsistencias = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoPlanAsistencias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoPlanAsistencia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoPlanAsistencias(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoPlanAsistenciaListModification', () => this.loadAll());
  }

  delete(tipoPlanAsistencia: ITipoPlanAsistencia): void {
    const modalRef = this.modalService.open(TipoPlanAsistenciaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoPlanAsistencia = tipoPlanAsistencia;
  }
}
