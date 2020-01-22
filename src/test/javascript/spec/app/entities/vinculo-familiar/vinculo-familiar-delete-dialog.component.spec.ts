import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Testmono04TestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { VinculoFamiliarDeleteDialogComponent } from 'app/entities/vinculo-familiar/vinculo-familiar-delete-dialog.component';
import { VinculoFamiliarService } from 'app/entities/vinculo-familiar/vinculo-familiar.service';

describe('Component Tests', () => {
  describe('VinculoFamiliar Management Delete Component', () => {
    let comp: VinculoFamiliarDeleteDialogComponent;
    let fixture: ComponentFixture<VinculoFamiliarDeleteDialogComponent>;
    let service: VinculoFamiliarService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [VinculoFamiliarDeleteDialogComponent]
      })
        .overrideTemplate(VinculoFamiliarDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VinculoFamiliarDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VinculoFamiliarService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.clear();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
