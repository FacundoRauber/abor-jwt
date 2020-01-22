import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Testmono04SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [Testmono04SharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class Testmono04HomeModule {}
