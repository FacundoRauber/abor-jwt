import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Testmono04TestModule } from '../../../test.module';
import { TipoTratamientoBasuraComponent } from 'app/entities/tipo-tratamiento-basura/tipo-tratamiento-basura.component';
import { TipoTratamientoBasuraService } from 'app/entities/tipo-tratamiento-basura/tipo-tratamiento-basura.service';
import { TipoTratamientoBasura } from 'app/shared/model/tipo-tratamiento-basura.model';

describe('Component Tests', () => {
  describe('TipoTratamientoBasura Management Component', () => {
    let comp: TipoTratamientoBasuraComponent;
    let fixture: ComponentFixture<TipoTratamientoBasuraComponent>;
    let service: TipoTratamientoBasuraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoTratamientoBasuraComponent],
        providers: []
      })
        .overrideTemplate(TipoTratamientoBasuraComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoTratamientoBasuraComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoTratamientoBasuraService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoTratamientoBasura(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoTratamientoBasuras && comp.tipoTratamientoBasuras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
