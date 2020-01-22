import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { TipoPlanAsistenciaDetailComponent } from 'app/entities/tipo-plan-asistencia/tipo-plan-asistencia-detail.component';
import { TipoPlanAsistencia } from 'app/shared/model/tipo-plan-asistencia.model';

describe('Component Tests', () => {
  describe('TipoPlanAsistencia Management Detail Component', () => {
    let comp: TipoPlanAsistenciaDetailComponent;
    let fixture: ComponentFixture<TipoPlanAsistenciaDetailComponent>;
    const route = ({ data: of({ tipoPlanAsistencia: new TipoPlanAsistencia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoPlanAsistenciaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoPlanAsistenciaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoPlanAsistenciaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoPlanAsistencia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoPlanAsistencia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
