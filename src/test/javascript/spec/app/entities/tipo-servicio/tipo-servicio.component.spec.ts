import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Testmono04TestModule } from '../../../test.module';
import { TipoServicioComponent } from 'app/entities/tipo-servicio/tipo-servicio.component';
import { TipoServicioService } from 'app/entities/tipo-servicio/tipo-servicio.service';
import { TipoServicio } from 'app/shared/model/tipo-servicio.model';

describe('Component Tests', () => {
  describe('TipoServicio Management Component', () => {
    let comp: TipoServicioComponent;
    let fixture: ComponentFixture<TipoServicioComponent>;
    let service: TipoServicioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoServicioComponent],
        providers: []
      })
        .overrideTemplate(TipoServicioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoServicioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoServicioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoServicio(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoServicios && comp.tipoServicios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
