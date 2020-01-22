import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INivelEducativo } from 'app/shared/model/nivel-educativo.model';
import { NivelEducativoService } from './nivel-educativo.service';
import { NivelEducativoDeleteDialogComponent } from './nivel-educativo-delete-dialog.component';

@Component({
  selector: 'jhi-nivel-educativo',
  templateUrl: './nivel-educativo.component.html'
})
export class NivelEducativoComponent implements OnInit, OnDestroy {
  nivelEducativos?: INivelEducativo[];
  eventSubscriber?: Subscription;

  constructor(
    protected nivelEducativoService: NivelEducativoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.nivelEducativoService.query().subscribe((res: HttpResponse<INivelEducativo[]>) => {
      this.nivelEducativos = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNivelEducativos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INivelEducativo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNivelEducativos(): void {
    this.eventSubscriber = this.eventManager.subscribe('nivelEducativoListModification', () => this.loadAll());
  }

  delete(nivelEducativo: INivelEducativo): void {
    const modalRef = this.modalService.open(NivelEducativoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nivelEducativo = nivelEducativo;
  }
}
