import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoOcupacion, TipoOcupacion } from 'app/shared/model/tipo-ocupacion.model';
import { TipoOcupacionService } from './tipo-ocupacion.service';
import { TipoOcupacionComponent } from './tipo-ocupacion.component';
import { TipoOcupacionDetailComponent } from './tipo-ocupacion-detail.component';
import { TipoOcupacionUpdateComponent } from './tipo-ocupacion-update.component';

@Injectable({ providedIn: 'root' })
export class TipoOcupacionResolve implements Resolve<ITipoOcupacion> {
  constructor(private service: TipoOcupacionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoOcupacion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoOcupacion: HttpResponse<TipoOcupacion>) => {
          if (tipoOcupacion.body) {
            return of(tipoOcupacion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoOcupacion());
  }
}

export const tipoOcupacionRoute: Routes = [
  {
    path: '',
    component: TipoOcupacionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoOcupacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoOcupacionDetailComponent,
    resolve: {
      tipoOcupacion: TipoOcupacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoOcupacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoOcupacionUpdateComponent,
    resolve: {
      tipoOcupacion: TipoOcupacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoOcupacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoOcupacionUpdateComponent,
    resolve: {
      tipoOcupacion: TipoOcupacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoOcupacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
