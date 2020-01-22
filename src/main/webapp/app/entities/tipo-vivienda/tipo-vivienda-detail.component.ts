import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoVivienda } from 'app/shared/model/tipo-vivienda.model';

@Component({
  selector: 'jhi-tipo-vivienda-detail',
  templateUrl: './tipo-vivienda-detail.component.html'
})
export class TipoViviendaDetailComponent implements OnInit {
  tipoVivienda: ITipoVivienda | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoVivienda }) => {
      this.tipoVivienda = tipoVivienda;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
