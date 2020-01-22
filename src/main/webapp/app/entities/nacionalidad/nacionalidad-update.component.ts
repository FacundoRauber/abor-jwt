import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INacionalidad, Nacionalidad } from 'app/shared/model/nacionalidad.model';
import { NacionalidadService } from './nacionalidad.service';

@Component({
  selector: 'jhi-nacionalidad-update',
  templateUrl: './nacionalidad-update.component.html'
})
export class NacionalidadUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    estado: []
  });

  constructor(protected nacionalidadService: NacionalidadService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nacionalidad }) => {
      this.updateForm(nacionalidad);
    });
  }

  updateForm(nacionalidad: INacionalidad): void {
    this.editForm.patchValue({
      id: nacionalidad.id,
      nombre: nacionalidad.nombre,
      estado: nacionalidad.estado
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nacionalidad = this.createFromForm();
    if (nacionalidad.id !== undefined) {
      this.subscribeToSaveResponse(this.nacionalidadService.update(nacionalidad));
    } else {
      this.subscribeToSaveResponse(this.nacionalidadService.create(nacionalidad));
    }
  }

  private createFromForm(): INacionalidad {
    return {
      ...new Nacionalidad(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      estado: this.editForm.get(['estado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INacionalidad>>): void {
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
