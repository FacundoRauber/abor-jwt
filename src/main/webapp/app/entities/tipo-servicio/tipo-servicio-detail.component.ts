import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoServicio } from 'app/shared/model/tipo-servicio.model';

@Component({
  selector: 'jhi-tipo-servicio-detail',
  templateUrl: './tipo-servicio-detail.component.html'
})
export class TipoServicioDetailComponent implements OnInit {
  tipoServicio: ITipoServicio | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoServicio }) => {
      this.tipoServicio = tipoServicio;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
