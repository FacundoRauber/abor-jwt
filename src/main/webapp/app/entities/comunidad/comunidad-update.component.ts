import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IComunidad, Comunidad } from 'app/shared/model/comunidad.model';
import { ComunidadService } from './comunidad.service';

@Component({
  selector: 'jhi-comunidad-update',
  templateUrl: './comunidad-update.component.html'
})
export class ComunidadUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    estado: []
  });

  constructor(protected comunidadService: ComunidadService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comunidad }) => {
      this.updateForm(comunidad);
    });
  }

  updateForm(comunidad: IComunidad): void {
    this.editForm.patchValue({
      id: comunidad.id,
      nombre: comunidad.nombre,
      estado: comunidad.estado
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comunidad = this.createFromForm();
    if (comunidad.id !== undefined) {
      this.subscribeToSaveResponse(this.comunidadService.update(comunidad));
    } else {
      this.subscribeToSaveResponse(this.comunidadService.create(comunidad));
    }
  }

  private createFromForm(): IComunidad {
    return {
      ...new Comunidad(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      estado: this.editForm.get(['estado'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComunidad>>): void {
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
