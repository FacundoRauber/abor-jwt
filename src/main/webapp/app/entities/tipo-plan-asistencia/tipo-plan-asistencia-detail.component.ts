import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoPlanAsistencia } from 'app/shared/model/tipo-plan-asistencia.model';

@Component({
  selector: 'jhi-tipo-plan-asistencia-detail',
  templateUrl: './tipo-plan-asistencia-detail.component.html'
})
export class TipoPlanAsistenciaDetailComponent implements OnInit {
  tipoPlanAsistencia: ITipoPlanAsistencia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoPlanAsistencia }) => {
      this.tipoPlanAsistencia = tipoPlanAsistencia;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
