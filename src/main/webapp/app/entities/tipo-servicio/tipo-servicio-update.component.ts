import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoServicio, TipoServicio } from 'app/shared/model/tipo-servicio.model';
import { TipoServicioService } from './tipo-servicio.service';

@Component({
  selector: 'jhi-tipo-servicio-update',
  templateUrl: './tipo-servicio-update.component.html'
})
export class TipoServicioUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    estado: []
  });

  constructor(protected tipoServicioService: TipoServicioService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoServicio }) => {
      this.updateForm(tipoServicio);
    });
  }

  updateForm(tipoServicio: ITipoServicio): void {
    this.editForm.patchValue({
      id: tipoServicio.id,
      nombre: tipoServicio.nombre,
      estado: tipoServicio.estado
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoServicio = this.createFromForm();
    if (tipoServicio.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoServicioService.update(tipoServicio));
    } else {
      this.subscribeToSaveResponse(this.tipoServicioService.create(tipoServicio));
    }
  }

  private createFromForm(): ITipoServicio {
    return {
      ...new TipoServicio(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      estado: this.editForm.get(['estado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoServicio>>): void {
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
