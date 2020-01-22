import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Testmono04TestModule } from '../../../test.module';
import { VinculoFamiliarDetailComponent } from 'app/entities/vinculo-familiar/vinculo-familiar-detail.component';
import { VinculoFamiliar } from 'app/shared/model/vinculo-familiar.model';

describe('Component Tests', () => {
  describe('VinculoFamiliar Management Detail Component', () => {
    let comp: VinculoFamiliarDetailComponent;
    let fixture: ComponentFixture<VinculoFamiliarDetailComponent>;
    const route = ({ data: of({ vinculoFamiliar: new VinculoFamiliar(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [VinculoFamiliarDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VinculoFamiliarDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VinculoFamiliarDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vinculoFamiliar on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vinculoFamiliar).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
