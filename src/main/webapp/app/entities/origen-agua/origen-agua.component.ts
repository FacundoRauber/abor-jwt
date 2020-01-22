import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrigenAgua } from 'app/shared/model/origen-agua.model';
import { OrigenAguaService } from './origen-agua.service';
import { OrigenAguaDeleteDialogComponent } from './origen-agua-delete-dialog.component';

@Component({
  selector: 'jhi-origen-agua',
  templateUrl: './origen-agua.component.html'
})
export class OrigenAguaComponent implements OnInit, OnDestroy {
  origenAguas?: IOrigenAgua[];
  eventSubscriber?: Subscription;

  constructor(protected origenAguaService: OrigenAguaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.origenAguaService.query().subscribe((res: HttpResponse<IOrigenAgua[]>) => {
      this.origenAguas = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrigenAguas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrigenAgua): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrigenAguas(): void {
    this.eventSubscriber = this.eventManager.subscribe('origenAguaListModification', () => this.loadAll());
  }

  delete(origenAgua: IOrigenAgua): void {
    const modalRef = this.modalService.open(OrigenAguaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.origenAgua = origenAgua;
  }
}
