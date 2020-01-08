import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DemoSharedModule } from 'app/shared/shared.module';
import { HeaderComponent } from './header.component';
import { HeaderDetailComponent } from './header-detail.component';
import { headerRoute } from './header.route';

@NgModule({
  imports: [DemoSharedModule, RouterModule.forChild(headerRoute)],
  declarations: [HeaderComponent, HeaderDetailComponent],
  entryComponents: []
})
export class DemoHeaderModule {}
