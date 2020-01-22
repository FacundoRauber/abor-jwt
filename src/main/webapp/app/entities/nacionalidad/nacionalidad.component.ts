import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INacionalidad } from 'app/shared/model/nacionalidad.model';
import { NacionalidadService } from './nacionalidad.service';
import { NacionalidadDeleteDialogComponent } from './nacionalidad-delete-dialog.component';

@Component({
  selector: 'jhi-nacionalidad',
  templateUrl: './nacionalidad.component.html'
})
export class NacionalidadComponent implements OnInit, OnDestroy {
  nacionalidads?: INacionalidad[];
  eventSubscriber?: Subscription;

  constructor(
    protected nacionalidadService: NacionalidadService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.nacionalidadService.query().subscribe((res: HttpResponse<INacionalidad[]>) => {
      this.nacionalidads = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNacionalidads();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INacionalidad): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNacionalidads(): void {
    this.eventSubscriber = this.eventManager.subscribe('nacionalidadListModification', () => this.loadAll());
  }

  delete(nacionalidad: INacionalidad): void {
    const modalRef = this.modalService.open(NacionalidadDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nacionalidad = nacionalidad;
  }
}
