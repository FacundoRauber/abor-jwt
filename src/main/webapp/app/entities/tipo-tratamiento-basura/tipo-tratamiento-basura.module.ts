import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { TipoTratamientoBasuraComponent } from './tipo-tratamiento-basura.component';
import { TipoTratamientoBasuraDetailComponent } from './tipo-tratamiento-basura-detail.component';
import { TipoTratamientoBasuraUpdateComponent } from './tipo-tratamiento-basura-update.component';
import { TipoTratamientoBasuraDeleteDialogComponent } from './tipo-tratamiento-basura-delete-dialog.component';
import { tipoTratamientoBasuraRoute } from './tipo-tratamiento-basura.route';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild(tipoTratamientoBasuraRoute)],
  declarations: [
    TipoTratamientoBasuraComponent,
    TipoTratamientoBasuraDetailComponent,
    TipoTratamientoBasuraUpdateComponent,
    TipoTratamientoBasuraDeleteDialogComponent
  ],
  entryComponents: [TipoTratamientoBasuraDeleteDialogComponent]
})
export class Testmono04TipoTratamientoBasuraModule {}
