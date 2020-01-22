import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { IntegranteDetailComponent } from 'app/entities/integrante/integrante-detail.component';
import { Integrante } from 'app/shared/model/integrante.model';

describe('Component Tests', () => {
  describe('Integrante Management Detail Component', () => {
    let comp: IntegranteDetailComponent;
    let fixture: ComponentFixture<IntegranteDetailComponent>;
    const route = ({ data: of({ integrante: new Integrante(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [IntegranteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IntegranteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IntegranteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load integrante on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.integrante).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
