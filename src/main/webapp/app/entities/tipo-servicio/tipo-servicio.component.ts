import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoServicio } from 'app/shared/model/tipo-servicio.model';
import { TipoServicioService } from './tipo-servicio.service';
import { TipoServicioDeleteDialogComponent } from './tipo-servicio-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-servicio',
  templateUrl: './tipo-servicio.component.html'
})
export class TipoServicioComponent implements OnInit, OnDestroy {
  tipoServicios?: ITipoServicio[];
  eventSubscriber?: Subscription;

  constructor(
    protected tipoServicioService: TipoServicioService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tipoServicioService.query().subscribe((res: HttpResponse<ITipoServicio[]>) => {
      this.tipoServicios = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoServicios();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoServicio): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoServicios(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoServicioListModification', () => this.loadAll());
  }

  delete(tipoServicio: ITipoServicio): void {
    const modalRef = this.modalService.open(TipoServicioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoServicio = tipoServicio;
  }
}
