import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { IntegranteUpdateComponent } from 'app/entities/integrante/integrante-update.component';
import { IntegranteService } from 'app/entities/integrante/integrante.service';
import { Integrante } from 'app/shared/model/integrante.model';

describe('Component Tests', () => {
  describe('Integrante Management Update Component', () => {
    let comp: IntegranteUpdateComponent;
    let fixture: ComponentFixture<IntegranteUpdateComponent>;
    let service: IntegranteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [IntegranteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IntegranteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IntegranteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IntegranteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Integrante(123);
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
        const entity = new Integrante();
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
