import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IComunidad } from 'app/shared/model/comunidad.model';
import { ComunidadService } from './comunidad.service';
import { ComunidadDeleteDialogComponent } from './comunidad-delete-dialog.component';

@Component({
  selector: 'jhi-comunidad',
  templateUrl: './comunidad.component.html'
})
export class ComunidadComponent implements OnInit, OnDestroy {
  comunidads?: IComunidad[];
  eventSubscriber?: Subscription;

  constructor(protected comunidadService: ComunidadService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.comunidadService.query().subscribe((res: HttpResponse<IComunidad[]>) => {
      this.comunidads = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInComunidads();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IComunidad): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInComunidads(): void {
    this.eventSubscriber = this.eventManager.subscribe('comunidadListModification', () => this.loadAll());
  }

  delete(comunidad: IComunidad): void {
    const modalRef = this.modalService.open(ComunidadDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.comunidad = comunidad;
  }
}
