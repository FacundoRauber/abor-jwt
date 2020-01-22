import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Testmono04TestModule } from '../../../test.module';
import { VinculoFamiliarComponent } from 'app/entities/vinculo-familiar/vinculo-familiar.component';
import { VinculoFamiliarService } from 'app/entities/vinculo-familiar/vinculo-familiar.service';
import { VinculoFamiliar } from 'app/shared/model/vinculo-familiar.model';

describe('Component Tests', () => {
  describe('VinculoFamiliar Management Component', () => {
    let comp: VinculoFamiliarComponent;
    let fixture: ComponentFixture<VinculoFamiliarComponent>;
    let service: VinculoFamiliarService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [VinculoFamiliarComponent],
        providers: []
      })
        .overrideTemplate(VinculoFamiliarComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VinculoFamiliarComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VinculoFamiliarService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VinculoFamiliar(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.vinculoFamiliars && comp.vinculoFamiliars[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
