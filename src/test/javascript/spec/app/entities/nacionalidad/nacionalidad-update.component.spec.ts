import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { NacionalidadUpdateComponent } from 'app/entities/nacionalidad/nacionalidad-update.component';
import { NacionalidadService } from 'app/entities/nacionalidad/nacionalidad.service';
import { Nacionalidad } from 'app/shared/model/nacionalidad.model';

describe('Component Tests', () => {
  describe('Nacionalidad Management Update Component', () => {
    let comp: NacionalidadUpdateComponent;
    let fixture: ComponentFixture<NacionalidadUpdateComponent>;
    let service: NacionalidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [NacionalidadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NacionalidadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NacionalidadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NacionalidadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Nacionalidad(123);
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
        const entity = new Nacionalidad();
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
