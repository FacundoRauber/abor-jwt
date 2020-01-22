import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoTratamientoBasura } from 'app/shared/model/tipo-tratamiento-basura.model';
import { TipoTratamientoBasuraService } from './tipo-tratamiento-basura.service';
import { TipoTratamientoBasuraDeleteDialogComponent } from './tipo-tratamiento-basura-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-tratamiento-basura',
  templateUrl: './tipo-tratamiento-basura.component.html'
})
export class TipoTratamientoBasuraComponent implements OnInit, OnDestroy {
  tipoTratamientoBasuras?: ITipoTratamientoBasura[];
  eventSubscriber?: Subscription;

  constructor(
    protected tipoTratamientoBasuraService: TipoTratamientoBasuraService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tipoTratamientoBasuraService.query().subscribe((res: HttpResponse<ITipoTratamientoBasura[]>) => {
      this.tipoTratamientoBasuras = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoTratamientoBasuras();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoTratamientoBasura): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoTratamientoBasuras(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoTratamientoBasuraListModification', () => this.loadAll());
  }

  delete(tipoTratamientoBasura: ITipoTratamientoBasura): void {
    const modalRef = this.modalService.open(TipoTratamientoBasuraDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoTratamientoBasura = tipoTratamientoBasura;
  }
}
