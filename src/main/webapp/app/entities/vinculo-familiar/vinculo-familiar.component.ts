import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVinculoFamiliar } from 'app/shared/model/vinculo-familiar.model';
import { VinculoFamiliarService } from './vinculo-familiar.service';
import { VinculoFamiliarDeleteDialogComponent } from './vinculo-familiar-delete-dialog.component';

@Component({
  selector: 'jhi-vinculo-familiar',
  templateUrl: './vinculo-familiar.component.html'
})
export class VinculoFamiliarComponent implements OnInit, OnDestroy {
  vinculoFamiliars?: IVinculoFamiliar[];
  eventSubscriber?: Subscription;

  constructor(
    protected vinculoFamiliarService: VinculoFamiliarService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.vinculoFamiliarService.query().subscribe((res: HttpResponse<IVinculoFamiliar[]>) => {
      this.vinculoFamiliars = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVinculoFamiliars();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVinculoFamiliar): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVinculoFamiliars(): void {
    this.eventSubscriber = this.eventManager.subscribe('vinculoFamiliarListModification', () => this.loadAll());
  }

  delete(vinculoFamiliar: IVinculoFamiliar): void {
    const modalRef = this.modalService.open(VinculoFamiliarDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vinculoFamiliar = vinculoFamiliar;
  }
}
