import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { TipoOcupacionDetailComponent } from 'app/entities/tipo-ocupacion/tipo-ocupacion-detail.component';
import { TipoOcupacion } from 'app/shared/model/tipo-ocupacion.model';

describe('Component Tests', () => {
  describe('TipoOcupacion Management Detail Component', () => {
    let comp: TipoOcupacionDetailComponent;
    let fixture: ComponentFixture<TipoOcupacionDetailComponent>;
    const route = ({ data: of({ tipoOcupacion: new TipoOcupacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoOcupacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoOcupacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoOcupacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoOcupacion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoOcupacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
