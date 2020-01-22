import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Testmono04TestModule } from '../../../test.module';
import { ComunidadComponent } from 'app/entities/comunidad/comunidad.component';
import { ComunidadService } from 'app/entities/comunidad/comunidad.service';
import { Comunidad } from 'app/shared/model/comunidad.model';

describe('Component Tests', () => {
  describe('Comunidad Management Component', () => {
    let comp: ComunidadComponent;
    let fixture: ComponentFixture<ComunidadComponent>;
    let service: ComunidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [ComunidadComponent],
        providers: []
      })
        .overrideTemplate(ComunidadComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComunidadComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComunidadService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Comunidad(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.comunidads && comp.comunidads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
