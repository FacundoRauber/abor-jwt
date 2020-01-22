import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrigenEnergia } from 'app/shared/model/origen-energia.model';
import { OrigenEnergiaService } from './origen-energia.service';
import { OrigenEnergiaDeleteDialogComponent } from './origen-energia-delete-dialog.component';

@Component({
  selector: 'jhi-origen-energia',
  templateUrl: './origen-energia.component.html'
})
export class OrigenEnergiaComponent implements OnInit, OnDestroy {
  origenEnergias?: IOrigenEnergia[];
  eventSubscriber?: Subscription;

  constructor(
    protected origenEnergiaService: OrigenEnergiaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.origenEnergiaService.query().subscribe((res: HttpResponse<IOrigenEnergia[]>) => {
      this.origenEnergias = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrigenEnergias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrigenEnergia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrigenEnergias(): void {
    this.eventSubscriber = this.eventManager.subscribe('origenEnergiaListModification', () => this.loadAll());
  }

  delete(origenEnergia: IOrigenEnergia): void {
    const modalRef = this.modalService.open(OrigenEnergiaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.origenEnergia = origenEnergia;
  }
}
