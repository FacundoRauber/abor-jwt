import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoPlanAsistencia, TipoPlanAsistencia } from 'app/shared/model/tipo-plan-asistencia.model';
import { TipoPlanAsistenciaService } from './tipo-plan-asistencia.service';

@Component({
  selector: 'jhi-tipo-plan-asistencia-update',
  templateUrl: './tipo-plan-asistencia-update.component.html'
})
export class TipoPlanAsistenciaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    estado: []
  });

  constructor(
    protected tipoPlanAsistenciaService: TipoPlanAsistenciaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoPlanAsistencia }) => {
      this.updateForm(tipoPlanAsistencia);
    });
  }

  updateForm(tipoPlanAsistencia: ITipoPlanAsistencia): void {
    this.editForm.patchValue({
      id: tipoPlanAsistencia.id,
      nombre: tipoPlanAsistencia.nombre,
      estado: tipoPlanAsistencia.estado
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoPlanAsistencia = this.createFromForm();
    if (tipoPlanAsistencia.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoPlanAsistenciaService.update(tipoPlanAsistencia));
    } else {
      this.subscribeToSaveResponse(this.tipoPlanAsistenciaService.create(tipoPlanAsistencia));
    }
  }

  private createFromForm(): ITipoPlanAsistencia {
    return {
      ...new TipoPlanAsistencia(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      estado: this.editForm.get(['estado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoPlanAsistencia>>): void {
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
