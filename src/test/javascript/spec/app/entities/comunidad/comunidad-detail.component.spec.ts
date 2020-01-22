import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { ComunidadDetailComponent } from 'app/entities/comunidad/comunidad-detail.component';
import { Comunidad } from 'app/shared/model/comunidad.model';

describe('Component Tests', () => {
  describe('Comunidad Management Detail Component', () => {
    let comp: ComunidadDetailComponent;
    let fixture: ComponentFixture<ComunidadDetailComponent>;
    const route = ({ data: of({ comunidad: new Comunidad(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [ComunidadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ComunidadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComunidadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load comunidad on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.comunidad).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
