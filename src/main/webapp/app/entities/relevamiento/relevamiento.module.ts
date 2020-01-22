import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { RelevamientoComponent } from './relevamiento.component';
import { RelevamientoDetailComponent } from './relevamiento-detail.component';
import { RelevamientoUpdateComponent } from './relevamiento-update.component';
import { RelevamientoDeleteDialogComponent } from './relevamiento-delete-dialog.component';
import { relevamientoRoute } from './relevamiento.route';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild(relevamientoRoute)],
  declarations: [RelevamientoComponent, RelevamientoDetailComponent, RelevamientoUpdateComponent, RelevamientoDeleteDialogComponent],
  entryComponents: [RelevamientoDeleteDialogComponent]
})
export class Testmono04RelevamientoModule {}
