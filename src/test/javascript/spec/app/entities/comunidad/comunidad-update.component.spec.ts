import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { ComunidadUpdateComponent } from 'app/entities/comunidad/comunidad-update.component';
import { ComunidadService } from 'app/entities/comunidad/comunidad.service';
import { Comunidad } from 'app/shared/model/comunidad.model';

describe('Component Tests', () => {
  describe('Comunidad Management Update Component', () => {
    let comp: ComunidadUpdateComponent;
    let fixture: ComponentFixture<ComunidadUpdateComponent>;
    let service: ComunidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [ComunidadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ComunidadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComunidadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComunidadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Comunidad(123);
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
        const entity = new Comunidad();
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
