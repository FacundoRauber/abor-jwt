import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoTratamientoBasura } from 'app/shared/model/tipo-tratamiento-basura.model';

@Component({
  selector: 'jhi-tipo-tratamiento-basura-detail',
  templateUrl: './tipo-tratamiento-basura-detail.component.html'
})
export class TipoTratamientoBasuraDetailComponent implements OnInit {
  tipoTratamientoBasura: ITipoTratamientoBasura | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoTratamientoBasura }) => {
      this.tipoTratamientoBasura = tipoTratamientoBasura;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
