import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { ComunidadComponent } from './comunidad.component';
import { ComunidadDetailComponent } from './comunidad-detail.component';
import { ComunidadUpdateComponent } from './comunidad-update.component';
import { ComunidadDeleteDialogComponent } from './comunidad-delete-dialog.component';
import { comunidadRoute } from './comunidad.route';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild(comunidadRoute)],
  declarations: [ComunidadComponent, ComunidadDetailComponent, ComunidadUpdateComponent, ComunidadDeleteDialogComponent],
  entryComponents: [ComunidadDeleteDialogComponent]
})
export class Testmono04ComunidadModule {}
