import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { TipoTratamientoBasuraDetailComponent } from 'app/entities/tipo-tratamiento-basura/tipo-tratamiento-basura-detail.component';
import { TipoTratamientoBasura } from 'app/shared/model/tipo-tratamiento-basura.model';

describe('Component Tests', () => {
  describe('TipoTratamientoBasura Management Detail Component', () => {
    let comp: TipoTratamientoBasuraDetailComponent;
    let fixture: ComponentFixture<TipoTratamientoBasuraDetailComponent>;
    const route = ({ data: of({ tipoTratamientoBasura: new TipoTratamientoBasura(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoTratamientoBasuraDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoTratamientoBasuraDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoTratamientoBasuraDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoTratamientoBasura on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoTratamientoBasura).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
