import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { NacionalidadDetailComponent } from 'app/entities/nacionalidad/nacionalidad-detail.component';
import { Nacionalidad } from 'app/shared/model/nacionalidad.model';

describe('Component Tests', () => {
  describe('Nacionalidad Management Detail Component', () => {
    let comp: NacionalidadDetailComponent;
    let fixture: ComponentFixture<NacionalidadDetailComponent>;
    const route = ({ data: of({ nacionalidad: new Nacionalidad(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [NacionalidadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NacionalidadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NacionalidadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nacionalidad on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nacionalidad).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
