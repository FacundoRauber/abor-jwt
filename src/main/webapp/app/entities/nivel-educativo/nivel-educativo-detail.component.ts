import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INivelEducativo } from 'app/shared/model/nivel-educativo.model';

@Component({
  selector: 'jhi-nivel-educativo-detail',
  templateUrl: './nivel-educativo-detail.component.html'
})
export class NivelEducativoDetailComponent implements OnInit {
  nivelEducativo: INivelEducativo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nivelEducativo }) => {
      this.nivelEducativo = nivelEducativo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
