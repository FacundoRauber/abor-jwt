import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoVivienda } from 'app/shared/model/tipo-vivienda.model';
import { TipoViviendaService } from './tipo-vivienda.service';
import { TipoViviendaDeleteDialogComponent } from './tipo-vivienda-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-vivienda',
  templateUrl: './tipo-vivienda.component.html'
})
export class TipoViviendaComponent implements OnInit, OnDestroy {
  tipoViviendas?: ITipoVivienda[];
  eventSubscriber?: Subscription;

  constructor(
    protected tipoViviendaService: TipoViviendaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tipoViviendaService.query().subscribe((res: HttpResponse<ITipoVivienda[]>) => {
      this.tipoViviendas = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoViviendas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoVivienda): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoViviendas(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoViviendaListModification', () => this.loadAll());
  }

  delete(tipoVivienda: ITipoVivienda): void {
    const modalRef = this.modalService.open(TipoViviendaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoVivienda = tipoVivienda;
  }
}
