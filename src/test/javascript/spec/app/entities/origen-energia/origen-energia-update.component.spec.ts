import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { OrigenEnergiaUpdateComponent } from 'app/entities/origen-energia/origen-energia-update.component';
import { OrigenEnergiaService } from 'app/entities/origen-energia/origen-energia.service';
import { OrigenEnergia } from 'app/shared/model/origen-energia.model';

describe('Component Tests', () => {
  describe('OrigenEnergia Management Update Component', () => {
    let comp: OrigenEnergiaUpdateComponent;
    let fixture: ComponentFixture<OrigenEnergiaUpdateComponent>;
    let service: OrigenEnergiaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [OrigenEnergiaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrigenEnergiaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrigenEnergiaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrigenEnergiaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrigenEnergia(123);
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
        const entity = new OrigenEnergia();
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
