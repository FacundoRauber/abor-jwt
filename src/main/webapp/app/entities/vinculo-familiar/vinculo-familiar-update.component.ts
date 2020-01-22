import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVinculoFamiliar, VinculoFamiliar } from 'app/shared/model/vinculo-familiar.model';
import { VinculoFamiliarService } from './vinculo-familiar.service';

@Component({
  selector: 'jhi-vinculo-familiar-update',
  templateUrl: './vinculo-familiar-update.component.html'
})
export class VinculoFamiliarUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    estado: []
  });

  constructor(
    protected vinculoFamiliarService: VinculoFamiliarService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vinculoFamiliar }) => {
      this.updateForm(vinculoFamiliar);
    });
  }

  updateForm(vinculoFamiliar: IVinculoFamiliar): void {
    this.editForm.patchValue({
      id: vinculoFamiliar.id,
      nombre: vinculoFamiliar.nombre,
      estado: vinculoFamiliar.estado
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vinculoFamiliar = this.createFromForm();
    if (vinculoFamiliar.id !== undefined) {
      this.subscribeToSaveResponse(this.vinculoFamiliarService.update(vinculoFamiliar));
    } else {
      this.subscribeToSaveResponse(this.vinculoFamiliarService.create(vinculoFamiliar));
    }
  }

  private createFromForm(): IVinculoFamiliar {
    return {
      ...new VinculoFamiliar(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      estado: this.editForm.get(['estado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVinculoFamiliar>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
