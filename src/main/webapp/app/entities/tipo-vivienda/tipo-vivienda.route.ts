import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoVivienda, TipoVivienda } from 'app/shared/model/tipo-vivienda.model';
import { TipoViviendaService } from './tipo-vivienda.service';
import { TipoViviendaComponent } from './tipo-vivienda.component';
import { TipoViviendaDetailComponent } from './tipo-vivienda-detail.component';
import { TipoViviendaUpdateComponent } from './tipo-vivienda-update.component';

@Injectable({ providedIn: 'root' })
export class TipoViviendaResolve implements Resolve<ITipoVivienda> {
  constructor(private service: TipoViviendaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoVivienda> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoVivienda: HttpResponse<TipoVivienda>) => {
          if (tipoVivienda.body) {
            return of(tipoVivienda.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoVivienda());
  }
}

export const tipoViviendaRoute: Routes = [
  {
    path: '',
    component: TipoViviendaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoVivienda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoViviendaDetailComponent,
    resolve: {
      tipoVivienda: TipoViviendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoVivienda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoViviendaUpdateComponent,
    resolve: {
      tipoVivienda: TipoViviendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoVivienda.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoViviendaUpdateComponent,
    resolve: {
      tipoVivienda: TipoViviendaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoVivienda.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
