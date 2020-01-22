import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { TipoViviendaDetailComponent } from 'app/entities/tipo-vivienda/tipo-vivienda-detail.component';
import { TipoVivienda } from 'app/shared/model/tipo-vivienda.model';

describe('Component Tests', () => {
  describe('TipoVivienda Management Detail Component', () => {
    let comp: TipoViviendaDetailComponent;
    let fixture: ComponentFixture<TipoViviendaDetailComponent>;
    const route = ({ data: of({ tipoVivienda: new TipoVivienda(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoViviendaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoViviendaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoViviendaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoVivienda on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoVivienda).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
