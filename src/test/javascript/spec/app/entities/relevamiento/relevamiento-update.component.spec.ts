import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { RelevamientoUpdateComponent } from 'app/entities/relevamiento/relevamiento-update.component';
import { RelevamientoService } from 'app/entities/relevamiento/relevamiento.service';
import { Relevamiento } from 'app/shared/model/relevamiento.model';

describe('Component Tests', () => {
  describe('Relevamiento Management Update Component', () => {
    let comp: RelevamientoUpdateComponent;
    let fixture: ComponentFixture<RelevamientoUpdateComponent>;
    let service: RelevamientoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [RelevamientoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RelevamientoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RelevamientoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RelevamientoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Relevamiento(123);
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
        const entity = new Relevamiento();
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
