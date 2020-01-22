import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Testmono04TestModule } from '../../../test.module';
import { OrigenAguaComponent } from 'app/entities/origen-agua/origen-agua.component';
import { OrigenAguaService } from 'app/entities/origen-agua/origen-agua.service';
import { OrigenAgua } from 'app/shared/model/origen-agua.model';

describe('Component Tests', () => {
  describe('OrigenAgua Management Component', () => {
    let comp: OrigenAguaComponent;
    let fixture: ComponentFixture<OrigenAguaComponent>;
    let service: OrigenAguaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [OrigenAguaComponent],
        providers: []
      })
        .overrideTemplate(OrigenAguaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrigenAguaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrigenAguaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrigenAgua(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.origenAguas && comp.origenAguas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
