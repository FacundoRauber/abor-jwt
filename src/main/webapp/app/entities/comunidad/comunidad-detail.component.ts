import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComunidad } from 'app/shared/model/comunidad.model';

@Component({
  selector: 'jhi-comunidad-detail',
  templateUrl: './comunidad-detail.component.html'
})
export class ComunidadDetailComponent implements OnInit {
  comunidad: IComunidad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comunidad }) => {
      this.comunidad = comunidad;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
