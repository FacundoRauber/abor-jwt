import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Testmono04TestModule } from '../../../test.module';
import { TipoPlanAsistenciaComponent } from 'app/entities/tipo-plan-asistencia/tipo-plan-asistencia.component';
import { TipoPlanAsistenciaService } from 'app/entities/tipo-plan-asistencia/tipo-plan-asistencia.service';
import { TipoPlanAsistencia } from 'app/shared/model/tipo-plan-asistencia.model';

describe('Component Tests', () => {
  describe('TipoPlanAsistencia Management Component', () => {
    let comp: TipoPlanAsistenciaComponent;
    let fixture: ComponentFixture<TipoPlanAsistenciaComponent>;
    let service: TipoPlanAsistenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoPlanAsistenciaComponent],
        providers: []
      })
        .overrideTemplate(TipoPlanAsistenciaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoPlanAsistenciaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoPlanAsistenciaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoPlanAsistencia(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoPlanAsistencias && comp.tipoPlanAsistencias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
