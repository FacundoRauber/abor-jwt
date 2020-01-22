import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { IntegranteComponent } from './integrante.component';
import { IntegranteDetailComponent } from './integrante-detail.component';
import { IntegranteUpdateComponent } from './integrante-update.component';
import { IntegranteDeleteDialogComponent } from './integrante-delete-dialog.component';
import { integranteRoute } from './integrante.route';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild(integranteRoute)],
  declarations: [IntegranteComponent, IntegranteDetailComponent, IntegranteUpdateComponent, IntegranteDeleteDialogComponent],
  entryComponents: [IntegranteDeleteDialogComponent]
})
export class Testmono04IntegranteModule {}
