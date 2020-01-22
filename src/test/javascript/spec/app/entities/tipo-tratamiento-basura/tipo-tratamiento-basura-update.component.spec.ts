import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { TipoTratamientoBasuraUpdateComponent } from 'app/entities/tipo-tratamiento-basura/tipo-tratamiento-basura-update.component';
import { TipoTratamientoBasuraService } from 'app/entities/tipo-tratamiento-basura/tipo-tratamiento-basura.service';
import { TipoTratamientoBasura } from 'app/shared/model/tipo-tratamiento-basura.model';

describe('Component Tests', () => {
  describe('TipoTratamientoBasura Management Update Component', () => {
    let comp: TipoTratamientoBasuraUpdateComponent;
    let fixture: ComponentFixture<TipoTratamientoBasuraUpdateComponent>;
    let service: TipoTratamientoBasuraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoTratamientoBasuraUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoTratamientoBasuraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoTratamientoBasuraUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoTratamientoBasuraService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoTratamientoBasura(123);
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
        const entity = new TipoTratamientoBasura();
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
