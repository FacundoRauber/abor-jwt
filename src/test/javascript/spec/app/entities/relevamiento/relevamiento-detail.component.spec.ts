import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { RelevamientoDetailComponent } from 'app/entities/relevamiento/relevamiento-detail.component';
import { Relevamiento } from 'app/shared/model/relevamiento.model';

describe('Component Tests', () => {
  describe('Relevamiento Management Detail Component', () => {
    let comp: RelevamientoDetailComponent;
    let fixture: ComponentFixture<RelevamientoDetailComponent>;
    const route = ({ data: of({ relevamiento: new Relevamiento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [RelevamientoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RelevamientoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RelevamientoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load relevamiento on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.relevamiento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
