import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INivelEducativo, NivelEducativo } from 'app/shared/model/nivel-educativo.model';
import { NivelEducativoService } from './nivel-educativo.service';

@Component({
  selector: 'jhi-nivel-educativo-update',
  templateUrl: './nivel-educativo-update.component.html'
})
export class NivelEducativoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    estado: []
  });

  constructor(protected nivelEducativoService: NivelEducativoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nivelEducativo }) => {
      this.updateForm(nivelEducativo);
    });
  }

  updateForm(nivelEducativo: INivelEducativo): void {
    this.editForm.patchValue({
      id: nivelEducativo.id,
      nombre: nivelEducativo.nombre,
      estado: nivelEducativo.estado
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nivelEducativo = this.createFromForm();
    if (nivelEducativo.id !== undefined) {
      this.subscribeToSaveResponse(this.nivelEducativoService.update(nivelEducativo));
    } else {
      this.subscribeToSaveResponse(this.nivelEducativoService.create(nivelEducativo));
    }
  }

  private createFromForm(): INivelEducativo {
    return {
      ...new NivelEducativo(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      estado: this.editForm.get(['estado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INivelEducativo>>): void {
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
