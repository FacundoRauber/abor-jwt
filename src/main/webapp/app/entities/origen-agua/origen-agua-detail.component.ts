import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrigenAgua } from 'app/shared/model/origen-agua.model';

@Component({
  selector: 'jhi-origen-agua-detail',
  templateUrl: './origen-agua-detail.component.html'
})
export class OrigenAguaDetailComponent implements OnInit {
  origenAgua: IOrigenAgua | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ origenAgua }) => {
      this.origenAgua = origenAgua;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
