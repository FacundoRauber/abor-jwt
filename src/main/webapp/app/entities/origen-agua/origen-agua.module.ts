import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { OrigenAguaComponent } from './origen-agua.component';
import { OrigenAguaDetailComponent } from './origen-agua-detail.component';
import { OrigenAguaUpdateComponent } from './origen-agua-update.component';
import { OrigenAguaDeleteDialogComponent } from './origen-agua-delete-dialog.component';
import { origenAguaRoute } from './origen-agua.route';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild(origenAguaRoute)],
  declarations: [OrigenAguaComponent, OrigenAguaDetailComponent, OrigenAguaUpdateComponent, OrigenAguaDeleteDialogComponent],
  entryComponents: [OrigenAguaDeleteDialogComponent]
})
export class Testmono04OrigenAguaModule {}
