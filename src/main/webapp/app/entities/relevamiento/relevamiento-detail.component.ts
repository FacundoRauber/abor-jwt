import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRelevamiento } from 'app/shared/model/relevamiento.model';

@Component({
  selector: 'jhi-relevamiento-detail',
  templateUrl: './relevamiento-detail.component.html'
})
export class RelevamientoDetailComponent implements OnInit {
  relevamiento: IRelevamiento | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relevamiento }) => {
      this.relevamiento = relevamiento;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
