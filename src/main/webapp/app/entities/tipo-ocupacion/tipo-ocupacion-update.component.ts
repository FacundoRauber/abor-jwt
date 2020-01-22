import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoOcupacion, TipoOcupacion } from 'app/shared/model/tipo-ocupacion.model';
import { TipoOcupacionService } from './tipo-ocupacion.service';

@Component({
  selector: 'jhi-tipo-ocupacion-update',
  templateUrl: './tipo-ocupacion-update.component.html'
})
export class TipoOcupacionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    estado: []
  });

  constructor(protected tipoOcupacionService: TipoOcupacionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoOcupacion }) => {
      this.updateForm(tipoOcupacion);
    });
  }

  updateForm(tipoOcupacion: ITipoOcupacion): void {
    this.editForm.patchValue({
      id: tipoOcupacion.id,
      nombre: tipoOcupacion.nombre,
      estado: tipoOcupacion.estado
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoOcupacion = this.createFromForm();
    if (tipoOcupacion.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoOcupacionService.update(tipoOcupacion));
    } else {
      this.subscribeToSaveResponse(this.tipoOcupacionService.create(tipoOcupacion));
    }
  }

  private createFromForm(): ITipoOcupacion {
    return {
      ...new TipoOcupacion(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      estado: this.editForm.get(['estado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoOcupacion>>): void {
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
