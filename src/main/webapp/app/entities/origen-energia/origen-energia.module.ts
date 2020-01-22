import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { OrigenEnergiaComponent } from './origen-energia.component';
import { OrigenEnergiaDetailComponent } from './origen-energia-detail.component';
import { OrigenEnergiaUpdateComponent } from './origen-energia-update.component';
import { OrigenEnergiaDeleteDialogComponent } from './origen-energia-delete-dialog.component';
import { origenEnergiaRoute } from './origen-energia.route';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild(origenEnergiaRoute)],
  declarations: [OrigenEnergiaComponent, OrigenEnergiaDetailComponent, OrigenEnergiaUpdateComponent, OrigenEnergiaDeleteDialogComponent],
  entryComponents: [OrigenEnergiaDeleteDialogComponent]
})
export class Testmono04OrigenEnergiaModule {}
