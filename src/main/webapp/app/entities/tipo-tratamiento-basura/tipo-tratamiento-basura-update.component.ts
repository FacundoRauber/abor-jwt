import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoTratamientoBasura, TipoTratamientoBasura } from 'app/shared/model/tipo-tratamiento-basura.model';
import { TipoTratamientoBasuraService } from './tipo-tratamiento-basura.service';

@Component({
  selector: 'jhi-tipo-tratamiento-basura-update',
  templateUrl: './tipo-tratamiento-basura-update.component.html'
})
export class TipoTratamientoBasuraUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    estado: []
  });

  constructor(
    protected tipoTratamientoBasuraService: TipoTratamientoBasuraService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoTratamientoBasura }) => {
      this.updateForm(tipoTratamientoBasura);
    });
  }

  updateForm(tipoTratamientoBasura: ITipoTratamientoBasura): void {
    this.editForm.patchValue({
      id: tipoTratamientoBasura.id,
      nombre: tipoTratamientoBasura.nombre,
      estado: tipoTratamientoBasura.estado
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoTratamientoBasura = this.createFromForm();
    if (tipoTratamientoBasura.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoTratamientoBasuraService.update(tipoTratamientoBasura));
    } else {
      this.subscribeToSaveResponse(this.tipoTratamientoBasuraService.create(tipoTratamientoBasura));
    }
  }

  private createFromForm(): ITipoTratamientoBasura {
    return {
      ...new TipoTratamientoBasura(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      estado: this.editForm.get(['estado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoTratamientoBasura>>): void {
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
