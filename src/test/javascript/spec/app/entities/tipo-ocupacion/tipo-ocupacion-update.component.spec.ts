import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { TipoOcupacionUpdateComponent } from 'app/entities/tipo-ocupacion/tipo-ocupacion-update.component';
import { TipoOcupacionService } from 'app/entities/tipo-ocupacion/tipo-ocupacion.service';
import { TipoOcupacion } from 'app/shared/model/tipo-ocupacion.model';

describe('Component Tests', () => {
  describe('TipoOcupacion Management Update Component', () => {
    let comp: TipoOcupacionUpdateComponent;
    let fixture: ComponentFixture<TipoOcupacionUpdateComponent>;
    let service: TipoOcupacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoOcupacionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoOcupacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoOcupacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoOcupacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoOcupacion(123);
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
        const entity = new TipoOcupacion();
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
