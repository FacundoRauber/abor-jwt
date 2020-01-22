import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVinculoFamiliar } from 'app/shared/model/vinculo-familiar.model';

@Component({
  selector: 'jhi-vinculo-familiar-detail',
  templateUrl: './vinculo-familiar-detail.component.html'
})
export class VinculoFamiliarDetailComponent implements OnInit {
  vinculoFamiliar: IVinculoFamiliar | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vinculoFamiliar }) => {
      this.vinculoFamiliar = vinculoFamiliar;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
