import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { NacionalidadComponent } from './nacionalidad.component';
import { NacionalidadDetailComponent } from './nacionalidad-detail.component';
import { NacionalidadUpdateComponent } from './nacionalidad-update.component';
import { NacionalidadDeleteDialogComponent } from './nacionalidad-delete-dialog.component';
import { nacionalidadRoute } from './nacionalidad.route';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild(nacionalidadRoute)],
  declarations: [NacionalidadComponent, NacionalidadDetailComponent, NacionalidadUpdateComponent, NacionalidadDeleteDialogComponent],
  entryComponents: [NacionalidadDeleteDialogComponent]
})
export class Testmono04NacionalidadModule {}
