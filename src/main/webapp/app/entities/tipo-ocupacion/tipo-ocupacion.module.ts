import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { TipoOcupacionComponent } from './tipo-ocupacion.component';
import { TipoOcupacionDetailComponent } from './tipo-ocupacion-detail.component';
import { TipoOcupacionUpdateComponent } from './tipo-ocupacion-update.component';
import { TipoOcupacionDeleteDialogComponent } from './tipo-ocupacion-delete-dialog.component';
import { tipoOcupacionRoute } from './tipo-ocupacion.route';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild(tipoOcupacionRoute)],
  declarations: [TipoOcupacionComponent, TipoOcupacionDetailComponent, TipoOcupacionUpdateComponent, TipoOcupacionDeleteDialogComponent],
  entryComponents: [TipoOcupacionDeleteDialogComponent]
})
export class Testmono04TipoOcupacionModule {}
