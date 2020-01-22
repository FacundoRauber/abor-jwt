import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIntegrante } from 'app/shared/model/integrante.model';

@Component({
  selector: 'jhi-integrante-detail',
  templateUrl: './integrante-detail.component.html'
})
export class IntegranteDetailComponent implements OnInit {
  integrante: IIntegrante | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ integrante }) => {
      this.integrante = integrante;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
