import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrigenEnergia } from 'app/shared/model/origen-energia.model';

@Component({
  selector: 'jhi-origen-energia-detail',
  templateUrl: './origen-energia-detail.component.html'
})
export class OrigenEnergiaDetailComponent implements OnInit {
  origenEnergia: IOrigenEnergia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ origenEnergia }) => {
      this.origenEnergia = origenEnergia;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
