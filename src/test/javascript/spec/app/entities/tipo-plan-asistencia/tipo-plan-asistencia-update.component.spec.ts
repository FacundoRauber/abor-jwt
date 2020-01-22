import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { TipoPlanAsistenciaUpdateComponent } from 'app/entities/tipo-plan-asistencia/tipo-plan-asistencia-update.component';
import { TipoPlanAsistenciaService } from 'app/entities/tipo-plan-asistencia/tipo-plan-asistencia.service';
import { TipoPlanAsistencia } from 'app/shared/model/tipo-plan-asistencia.model';

describe('Component Tests', () => {
  describe('TipoPlanAsistencia Management Update Component', () => {
    let comp: TipoPlanAsistenciaUpdateComponent;
    let fixture: ComponentFixture<TipoPlanAsistenciaUpdateComponent>;
    let service: TipoPlanAsistenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoPlanAsistenciaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoPlanAsistenciaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoPlanAsistenciaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoPlanAsistenciaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoPlanAsistencia(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoPlanAsistencia();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
