import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { TipoViviendaUpdateComponent } from 'app/entities/tipo-vivienda/tipo-vivienda-update.component';
import { TipoViviendaService } from 'app/entities/tipo-vivienda/tipo-vivienda.service';
import { TipoVivienda } from 'app/shared/model/tipo-vivienda.model';

describe('Component Tests', () => {
  describe('TipoVivienda Management Update Component', () => {
    let comp: TipoViviendaUpdateComponent;
    let fixture: ComponentFixture<TipoViviendaUpdateComponent>;
    let service: TipoViviendaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoViviendaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoViviendaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoViviendaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoViviendaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoVivienda(123);
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
        const entity = new TipoVivienda();
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
