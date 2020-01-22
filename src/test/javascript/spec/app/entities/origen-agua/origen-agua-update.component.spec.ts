import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { OrigenAguaUpdateComponent } from 'app/entities/origen-agua/origen-agua-update.component';
import { OrigenAguaService } from 'app/entities/origen-agua/origen-agua.service';
import { OrigenAgua } from 'app/shared/model/origen-agua.model';

describe('Component Tests', () => {
  describe('OrigenAgua Management Update Component', () => {
    let comp: OrigenAguaUpdateComponent;
    let fixture: ComponentFixture<OrigenAguaUpdateComponent>;
    let service: OrigenAguaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [OrigenAguaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrigenAguaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrigenAguaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrigenAguaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrigenAgua(123);
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
        const entity = new OrigenAgua();
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
