import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Testmono04TestModule } from '../../../test.module';
import { TipoOcupacionComponent } from 'app/entities/tipo-ocupacion/tipo-ocupacion.component';
import { TipoOcupacionService } from 'app/entities/tipo-ocupacion/tipo-ocupacion.service';
import { TipoOcupacion } from 'app/shared/model/tipo-ocupacion.model';

describe('Component Tests', () => {
  describe('TipoOcupacion Management Component', () => {
    let comp: TipoOcupacionComponent;
    let fixture: ComponentFixture<TipoOcupacionComponent>;
    let service: TipoOcupacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoOcupacionComponent],
        providers: []
      })
        .overrideTemplate(TipoOcupacionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoOcupacionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoOcupacionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoOcupacion(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoOcupacions && comp.tipoOcupacions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
