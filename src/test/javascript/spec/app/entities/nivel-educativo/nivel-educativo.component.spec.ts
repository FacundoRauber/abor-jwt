import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Testmono04TestModule } from '../../../test.module';
import { NivelEducativoComponent } from 'app/entities/nivel-educativo/nivel-educativo.component';
import { NivelEducativoService } from 'app/entities/nivel-educativo/nivel-educativo.service';
import { NivelEducativo } from 'app/shared/model/nivel-educativo.model';

describe('Component Tests', () => {
  describe('NivelEducativo Management Component', () => {
    let comp: NivelEducativoComponent;
    let fixture: ComponentFixture<NivelEducativoComponent>;
    let service: NivelEducativoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [NivelEducativoComponent],
        providers: []
      })
        .overrideTemplate(NivelEducativoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NivelEducativoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NivelEducativoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NivelEducativo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.nivelEducativos && comp.nivelEducativos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
