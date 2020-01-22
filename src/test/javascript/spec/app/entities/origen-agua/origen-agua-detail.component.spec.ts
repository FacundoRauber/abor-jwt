import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { OrigenAguaDetailComponent } from 'app/entities/origen-agua/origen-agua-detail.component';
import { OrigenAgua } from 'app/shared/model/origen-agua.model';

describe('Component Tests', () => {
  describe('OrigenAgua Management Detail Component', () => {
    let comp: OrigenAguaDetailComponent;
    let fixture: ComponentFixture<OrigenAguaDetailComponent>;
    const route = ({ data: of({ origenAgua: new OrigenAgua(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [OrigenAguaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrigenAguaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrigenAguaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load origenAgua on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.origenAgua).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
