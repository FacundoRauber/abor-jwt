import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { NivelEducativoUpdateComponent } from 'app/entities/nivel-educativo/nivel-educativo-update.component';
import { NivelEducativoService } from 'app/entities/nivel-educativo/nivel-educativo.service';
import { NivelEducativo } from 'app/shared/model/nivel-educativo.model';

describe('Component Tests', () => {
  describe('NivelEducativo Management Update Component', () => {
    let comp: NivelEducativoUpdateComponent;
    let fixture: ComponentFixture<NivelEducativoUpdateComponent>;
    let service: NivelEducativoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [NivelEducativoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NivelEducativoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NivelEducativoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NivelEducativoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NivelEducativo(123);
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
        const entity = new NivelEducativo();
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
