import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoOcupacion } from 'app/shared/model/tipo-ocupacion.model';
import { TipoOcupacionService } from './tipo-ocupacion.service';
import { TipoOcupacionDeleteDialogComponent } from './tipo-ocupacion-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-ocupacion',
  templateUrl: './tipo-ocupacion.component.html'
})
export class TipoOcupacionComponent implements OnInit, OnDestroy {
  tipoOcupacions?: ITipoOcupacion[];
  eventSubscriber?: Subscription;

  constructor(
    protected tipoOcupacionService: TipoOcupacionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tipoOcupacionService.query().subscribe((res: HttpResponse<ITipoOcupacion[]>) => {
      this.tipoOcupacions = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoOcupacions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoOcupacion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoOcupacions(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoOcupacionListModification', () => this.loadAll());
  }

  delete(tipoOcupacion: ITipoOcupacion): void {
    const modalRef = this.modalService.open(TipoOcupacionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoOcupacion = tipoOcupacion;
  }
}
