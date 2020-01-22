import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { VinculoFamiliarComponent } from './vinculo-familiar.component';
import { VinculoFamiliarDetailComponent } from './vinculo-familiar-detail.component';
import { VinculoFamiliarUpdateComponent } from './vinculo-familiar-update.component';
import { VinculoFamiliarDeleteDialogComponent } from './vinculo-familiar-delete-dialog.component';
import { vinculoFamiliarRoute } from './vinculo-familiar.route';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild(vinculoFamiliarRoute)],
  declarations: [
    VinculoFamiliarComponent,
    VinculoFamiliarDetailComponent,
    VinculoFamiliarUpdateComponent,
    VinculoFamiliarDeleteDialogComponent
  ],
  entryComponents: [VinculoFamiliarDeleteDialogComponent]
})
export class Testmono04VinculoFamiliarModule {}
