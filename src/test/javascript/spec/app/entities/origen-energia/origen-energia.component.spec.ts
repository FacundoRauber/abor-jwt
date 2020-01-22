import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Testmono04TestModule } from '../../../test.module';
import { OrigenEnergiaComponent } from 'app/entities/origen-energia/origen-energia.component';
import { OrigenEnergiaService } from 'app/entities/origen-energia/origen-energia.service';
import { OrigenEnergia } from 'app/shared/model/origen-energia.model';

describe('Component Tests', () => {
  describe('OrigenEnergia Management Component', () => {
    let comp: OrigenEnergiaComponent;
    let fixture: ComponentFixture<OrigenEnergiaComponent>;
    let service: OrigenEnergiaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [OrigenEnergiaComponent],
        providers: []
      })
        .overrideTemplate(OrigenEnergiaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrigenEnergiaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrigenEnergiaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrigenEnergia(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.origenEnergias && comp.origenEnergias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
