import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIntegrante } from 'app/shared/model/integrante.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IntegranteService } from './integrante.service';
import { IntegranteDeleteDialogComponent } from './integrante-delete-dialog.component';

@Component({
  selector: 'jhi-integrante',
  templateUrl: './integrante.component.html'
})
export class IntegranteComponent implements OnInit, OnDestroy {
  integrantes: IIntegrante[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected integranteService: IntegranteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.integrantes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.integranteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IIntegrante[]>) => this.paginateIntegrantes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.integrantes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIntegrantes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIntegrante): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIntegrantes(): void {
    this.eventSubscriber = this.eventManager.subscribe('integranteListModification', () => this.reset());
  }

  delete(integrante: IIntegrante): void {
    const modalRef = this.modalService.open(IntegranteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.integrante = integrante;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateIntegrantes(data: IIntegrante[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.integrantes.push(data[i]);
      }
    }
  }
}
