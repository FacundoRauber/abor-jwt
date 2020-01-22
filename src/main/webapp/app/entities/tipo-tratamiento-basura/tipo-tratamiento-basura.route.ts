import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoTratamientoBasura, TipoTratamientoBasura } from 'app/shared/model/tipo-tratamiento-basura.model';
import { TipoTratamientoBasuraService } from './tipo-tratamiento-basura.service';
import { TipoTratamientoBasuraComponent } from './tipo-tratamiento-basura.component';
import { TipoTratamientoBasuraDetailComponent } from './tipo-tratamiento-basura-detail.component';
import { TipoTratamientoBasuraUpdateComponent } from './tipo-tratamiento-basura-update.component';

@Injectable({ providedIn: 'root' })
export class TipoTratamientoBasuraResolve implements Resolve<ITipoTratamientoBasura> {
  constructor(private service: TipoTratamientoBasuraService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoTratamientoBasura> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoTratamientoBasura: HttpResponse<TipoTratamientoBasura>) => {
          if (tipoTratamientoBasura.body) {
            return of(tipoTratamientoBasura.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoTratamientoBasura());
  }
}

export const tipoTratamientoBasuraRoute: Routes = [
  {
    path: '',
    component: TipoTratamientoBasuraComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoTratamientoBasura.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoTratamientoBasuraDetailComponent,
    resolve: {
      tipoTratamientoBasura: TipoTratamientoBasuraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoTratamientoBasura.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoTratamientoBasuraUpdateComponent,
    resolve: {
      tipoTratamientoBasura: TipoTratamientoBasuraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoTratamientoBasura.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoTratamientoBasuraUpdateComponent,
    resolve: {
      tipoTratamientoBasura: TipoTratamientoBasuraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoTratamientoBasura.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
