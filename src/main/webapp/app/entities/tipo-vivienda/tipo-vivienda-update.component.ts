import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoVivienda, TipoVivienda } from 'app/shared/model/tipo-vivienda.model';
import { TipoViviendaService } from './tipo-vivienda.service';

@Component({
  selector: 'jhi-tipo-vivienda-update',
  templateUrl: './tipo-vivienda-update.component.html'
})
export class TipoViviendaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    estado: []
  });

  constructor(protected tipoViviendaService: TipoViviendaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoVivienda }) => {
      this.updateForm(tipoVivienda);
    });
  }

  updateForm(tipoVivienda: ITipoVivienda): void {
    this.editForm.patchValue({
      id: tipoVivienda.id,
      nombre: tipoVivienda.nombre,
      estado: tipoVivienda.estado
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoVivienda = this.createFromForm();
    if (tipoVivienda.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoViviendaService.update(tipoVivienda));
    } else {
      this.subscribeToSaveResponse(this.tipoViviendaService.create(tipoVivienda));
    }
  }

  private createFromForm(): ITipoVivienda {
    return {
      ...new TipoVivienda(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      estado: this.editForm.get(['estado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoVivienda>>): void {
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
