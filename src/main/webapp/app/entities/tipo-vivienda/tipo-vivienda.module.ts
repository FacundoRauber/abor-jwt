import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { TipoViviendaComponent } from './tipo-vivienda.component';
import { TipoViviendaDetailComponent } from './tipo-vivienda-detail.component';
import { TipoViviendaUpdateComponent } from './tipo-vivienda-update.component';
import { TipoViviendaDeleteDialogComponent } from './tipo-vivienda-delete-dialog.component';
import { tipoViviendaRoute } from './tipo-vivienda.route';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild(tipoViviendaRoute)],
  declarations: [TipoViviendaComponent, TipoViviendaDetailComponent, TipoViviendaUpdateComponent, TipoViviendaDeleteDialogComponent],
  entryComponents: [TipoViviendaDeleteDialogComponent]
})
export class Testmono04TipoViviendaModule {}
