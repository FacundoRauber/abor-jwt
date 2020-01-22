import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INacionalidad } from 'app/shared/model/nacionalidad.model';

@Component({
  selector: 'jhi-nacionalidad-detail',
  templateUrl: './nacionalidad-detail.component.html'
})
export class NacionalidadDetailComponent implements OnInit {
  nacionalidad: INacionalidad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nacionalidad }) => {
      this.nacionalidad = nacionalidad;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
