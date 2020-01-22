import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { TipoPlanAsistenciaComponent } from './tipo-plan-asistencia.component';
import { TipoPlanAsistenciaDetailComponent } from './tipo-plan-asistencia-detail.component';
import { TipoPlanAsistenciaUpdateComponent } from './tipo-plan-asistencia-update.component';
import { TipoPlanAsistenciaDeleteDialogComponent } from './tipo-plan-asistencia-delete-dialog.component';
import { tipoPlanAsistenciaRoute } from './tipo-plan-asistencia.route';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild(tipoPlanAsistenciaRoute)],
  declarations: [
    TipoPlanAsistenciaComponent,
    TipoPlanAsistenciaDetailComponent,
    TipoPlanAsistenciaUpdateComponent,
    TipoPlanAsistenciaDeleteDialogComponent
  ],
  entryComponents: [TipoPlanAsistenciaDeleteDialogComponent]
})
export class Testmono04TipoPlanAsistenciaModule {}
