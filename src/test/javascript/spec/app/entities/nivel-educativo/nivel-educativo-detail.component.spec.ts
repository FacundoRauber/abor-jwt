import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { NivelEducativoDetailComponent } from 'app/entities/nivel-educativo/nivel-educativo-detail.component';
import { NivelEducativo } from 'app/shared/model/nivel-educativo.model';

describe('Component Tests', () => {
  describe('NivelEducativo Management Detail Component', () => {
    let comp: NivelEducativoDetailComponent;
    let fixture: ComponentFixture<NivelEducativoDetailComponent>;
    const route = ({ data: of({ nivelEducativo: new NivelEducativo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [NivelEducativoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NivelEducativoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NivelEducativoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nivelEducativo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nivelEducativo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
