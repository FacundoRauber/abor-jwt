import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIntegrante, Integrante } from 'app/shared/model/integrante.model';
import { IntegranteService } from './integrante.service';
import { IntegranteComponent } from './integrante.component';
import { IntegranteDetailComponent } from './integrante-detail.component';
import { IntegranteUpdateComponent } from './integrante-update.component';

@Injectable({ providedIn: 'root' })
export class IntegranteResolve implements Resolve<IIntegrante> {
  constructor(private service: IntegranteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIntegrante> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((integrante: HttpResponse<Integrante>) => {
          if (integrante.body) {
            return of(integrante.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Integrante());
  }
}

export const integranteRoute: Routes = [
  {
    path: '',
    component: IntegranteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.integrante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IntegranteDetailComponent,
    resolve: {
      integrante: IntegranteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.integrante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IntegranteUpdateComponent,
    resolve: {
      integrante: IntegranteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.integrante.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IntegranteUpdateComponent,
    resolve: {
      integrante: IntegranteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.integrante.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
