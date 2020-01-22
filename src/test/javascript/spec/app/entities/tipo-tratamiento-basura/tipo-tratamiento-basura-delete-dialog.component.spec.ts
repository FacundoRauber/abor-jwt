import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Testmono04TestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { TipoTratamientoBasuraDeleteDialogComponent } from 'app/entities/tipo-tratamiento-basura/tipo-tratamiento-basura-delete-dialog.component';
import { TipoTratamientoBasuraService } from 'app/entities/tipo-tratamiento-basura/tipo-tratamiento-basura.service';

describe('Component Tests', () => {
  describe('TipoTratamientoBasura Management Delete Component', () => {
    let comp: TipoTratamientoBasuraDeleteDialogComponent;
    let fixture: ComponentFixture<TipoTratamientoBasuraDeleteDialogComponent>;
    let service: TipoTratamientoBasuraService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Testmono04TestModule],
        declarations: [TipoTratamientoBasuraDeleteDialogComponent]
      })
        .overrideTemplate(TipoTratamientoBasuraDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoTratamientoBasuraDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoTratamientoBasuraService);
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
