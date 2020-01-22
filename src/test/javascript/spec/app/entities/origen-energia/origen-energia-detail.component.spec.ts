import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { OrigenEnergiaDetailComponent } from 'app/entities/origen-energia/origen-energia-detail.component';
import { OrigenEnergia } from 'app/shared/model/origen-energia.model';

describe('Component Tests', () => {
  describe('OrigenEnergia Management Detail Component', () => {
    let comp: OrigenEnergiaDetailComponent;
    let fixture: ComponentFixture<OrigenEnergiaDetailComponent>;
    const route = ({ data: of({ origenEnergia: new OrigenEnergia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [OrigenEnergiaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrigenEnergiaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrigenEnergiaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load origenEnergia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.origenEnergia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
