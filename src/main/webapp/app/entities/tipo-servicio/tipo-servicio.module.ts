import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { TipoServicioComponent } from './tipo-servicio.component';
import { TipoServicioDetailComponent } from './tipo-servicio-detail.component';
import { TipoServicioUpdateComponent } from './tipo-servicio-update.component';
import { TipoServicioDeleteDialogComponent } from './tipo-servicio-delete-dialog.component';
import { tipoServicioRoute } from './tipo-servicio.route';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild(tipoServicioRoute)],
  declarations: [TipoServicioComponent, TipoServicioDetailComponent, TipoServicioUpdateComponent, TipoServicioDeleteDialogComponent],
  entryComponents: [TipoServicioDeleteDialogComponent]
})
export class Testmono04TipoServicioModule {}
