import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoServicio, TipoServicio } from 'app/shared/model/tipo-servicio.model';
import { TipoServicioService } from './tipo-servicio.service';
import { TipoServicioComponent } from './tipo-servicio.component';
import { TipoServicioDetailComponent } from './tipo-servicio-detail.component';
import { TipoServicioUpdateComponent } from './tipo-servicio-update.component';

@Injectable({ providedIn: 'root' })
export class TipoServicioResolve implements Resolve<ITipoServicio> {
  constructor(private service: TipoServicioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoServicio> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoServicio: HttpResponse<TipoServicio>) => {
          if (tipoServicio.body) {
            return of(tipoServicio.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoServicio());
  }
}

export const tipoServicioRoute: Routes = [
  {
    path: '',
    component: TipoServicioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoServicio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoServicioDetailComponent,
    resolve: {
      tipoServicio: TipoServicioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoServicio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoServicioUpdateComponent,
    resolve: {
      tipoServicio: TipoServicioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoServicio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoServicioUpdateComponent,
    resolve: {
      tipoServicio: TipoServicioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoServicio.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
