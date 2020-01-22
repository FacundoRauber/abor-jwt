import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Testmono04TestModule } from '../../../test.module';
import { TipoViviendaComponent } from 'app/entities/tipo-vivienda/tipo-vivienda.component';
import { TipoViviendaService } from 'app/entities/tipo-vivienda/tipo-vivienda.service';
import { TipoVivienda } from 'app/shared/model/tipo-vivienda.model';

describe('Component Tests', () => {
  describe('TipoVivienda Management Component', () => {
    let comp: TipoViviendaComponent;
    let fixture: ComponentFixture<TipoViviendaComponent>;
    let service: TipoViviendaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoViviendaComponent],
        providers: []
      })
        .overrideTemplate(TipoViviendaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoViviendaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoViviendaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoVivienda(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoViviendas && comp.tipoViviendas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
