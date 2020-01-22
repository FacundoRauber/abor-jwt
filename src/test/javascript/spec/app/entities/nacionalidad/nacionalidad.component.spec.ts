import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Testmono04TestModule } from '../../../test.module';
import { NacionalidadComponent } from 'app/entities/nacionalidad/nacionalidad.component';
import { NacionalidadService } from 'app/entities/nacionalidad/nacionalidad.service';
import { Nacionalidad } from 'app/shared/model/nacionalidad.model';

describe('Component Tests', () => {
  describe('Nacionalidad Management Component', () => {
    let comp: NacionalidadComponent;
    let fixture: ComponentFixture<NacionalidadComponent>;
    let service: NacionalidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [NacionalidadComponent],
        providers: []
      })
        .overrideTemplate(NacionalidadComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NacionalidadComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NacionalidadService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Nacionalidad(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.nacionalidads && comp.nacionalidads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
