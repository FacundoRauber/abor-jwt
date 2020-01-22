import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IRelevamiento, Relevamiento } from 'app/shared/model/relevamiento.model';
import { RelevamientoService } from './relevamiento.service';
import { IIntegrante } from 'app/shared/model/integrante.model';
import { IntegranteService } from 'app/entities/integrante/integrante.service';
import { IOrigenEnergia } from 'app/shared/model/origen-energia.model';
import { OrigenEnergiaService } from 'app/entities/origen-energia/origen-energia.service';
import { IOrigenAgua } from 'app/shared/model/origen-agua.model';
import { OrigenAguaService } from 'app/entities/origen-agua/origen-agua.service';
import { ITipoServicio } from 'app/shared/model/tipo-servicio.model';
import { TipoServicioService } from 'app/entities/tipo-servicio/tipo-servicio.service';

type SelectableEntity = IIntegrante | IOrigenEnergia | IOrigenAgua | ITipoServicio;

@Component({
  selector: 'jhi-relevamiento-update',
  templateUrl: './relevamiento-update.component.html'
})
export class RelevamientoUpdateComponent implements OnInit {
  isSaving = false;

  integrantes: IIntegrante[] = [];

  origenenergias: IOrigenEnergia[] = [];

  origenaguas: IOrigenAgua[] = [];

  tiposervicios: ITipoServicio[] = [];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    fecha: [null, [Validators.required]],
    escuela: [],
    puestoSalud: [],
    estado: [],
    integrante: [],
    origenenergia: [],
    origenagua: [],
    tiposervicio: []
  });

  constructor(
    protected relevamientoService: RelevamientoService,
    protected integranteService: IntegranteService,
    protected origenEnergiaService: OrigenEnergiaService,
    protected origenAguaService: OrigenAguaService,
    protected tipoServicioService: TipoServicioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ relevamiento }) => {
      this.updateForm(relevamiento);

      this.integranteService
        .query()
        .pipe(
          map((res: HttpResponse<IIntegrante[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IIntegrante[]) => (this.integrantes = resBody));

      this.origenEnergiaService
        .query()
        .pipe(
          map((res: HttpResponse<IOrigenEnergia[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IOrigenEnergia[]) => (this.origenenergias = resBody));

      this.origenAguaService
        .query()
        .pipe(
          map((res: HttpResponse<IOrigenAgua[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IOrigenAgua[]) => (this.origenaguas = resBody));

      this.tipoServicioService
        .query()
        .pipe(
          map((res: HttpResponse<ITipoServicio[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ITipoServicio[]) => (this.tiposervicios = resBody));
    });
  }

  updateForm(relevamiento: IRelevamiento): void {
    this.editForm.patchValue({
      id: relevamiento.id,
      fecha: relevamiento.fecha,
      escuela: relevamiento.escuela,
      puestoSalud: relevamiento.puestoSalud,
      estado: relevamiento.estado,
      integrante: relevamiento.integrante,
      origenenergia: relevamiento.origenenergia,
      origenagua: relevamiento.origenagua,
      tiposervicio: relevamiento.tiposervicio
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const relevamiento = this.createFromForm();
    if (relevamiento.id !== undefined) {
      this.subscribeToSaveResponse(this.relevamientoService.update(relevamiento));
    } else {
      this.subscribeToSaveResponse(this.relevamientoService.create(relevamiento));
    }
  }

  private createFromForm(): IRelevamiento {
    return {
      ...new Relevamiento(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value,
      escuela: this.editForm.get(['escuela'])!.value,
      puestoSalud: this.editForm.get(['puestoSalud'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      integrante: this.editForm.get(['integrante'])!.value,
      origenenergia: this.editForm.get(['origenenergia'])!.value,
      origenagua: this.editForm.get(['origenagua'])!.value,
      tiposervicio: this.editForm.get(['tiposervicio'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRelevamiento>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
