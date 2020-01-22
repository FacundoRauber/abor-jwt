import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { NivelEducativoComponent } from './nivel-educativo.component';
import { NivelEducativoDetailComponent } from './nivel-educativo-detail.component';
import { NivelEducativoUpdateComponent } from './nivel-educativo-update.component';
import { NivelEducativoDeleteDialogComponent } from './nivel-educativo-delete-dialog.component';
import { nivelEducativoRoute } from './nivel-educativo.route';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild(nivelEducativoRoute)],
  declarations: [
    NivelEducativoComponent,
    NivelEducativoDetailComponent,
    NivelEducativoUpdateComponent,
    NivelEducativoDeleteDialogComponent
  ],
  entryComponents: [NivelEducativoDeleteDialogComponent]
})
export class Testmono04NivelEducativoModule {}
