import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { VinculoFamiliarUpdateComponent } from 'app/entities/vinculo-familiar/vinculo-familiar-update.component';
import { VinculoFamiliarService } from 'app/entities/vinculo-familiar/vinculo-familiar.service';
import { VinculoFamiliar } from 'app/shared/model/vinculo-familiar.model';

describe('Component Tests', () => {
  describe('VinculoFamiliar Management Update Component', () => {
    let comp: VinculoFamiliarUpdateComponent;
    let fixture: ComponentFixture<VinculoFamiliarUpdateComponent>;
    let service: VinculoFamiliarService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [VinculoFamiliarUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VinculoFamiliarUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VinculoFamiliarUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VinculoFamiliarService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VinculoFamiliar(123);
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
        const entity = new VinculoFamiliar();
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
