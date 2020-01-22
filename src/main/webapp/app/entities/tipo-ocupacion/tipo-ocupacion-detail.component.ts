import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoOcupacion } from 'app/shared/model/tipo-ocupacion.model';

@Component({
  selector: 'jhi-tipo-ocupacion-detail',
  templateUrl: './tipo-ocupacion-detail.component.html'
})
export class TipoOcupacionDetailComponent implements OnInit {
  tipoOcupacion: ITipoOcupacion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoOcupacion }) => {
      this.tipoOcupacion = tipoOcupacion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
