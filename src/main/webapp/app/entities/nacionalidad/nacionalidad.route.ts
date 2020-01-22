import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INacionalidad, Nacionalidad } from 'app/shared/model/nacionalidad.model';
import { NacionalidadService } from './nacionalidad.service';
import { NacionalidadComponent } from './nacionalidad.component';
import { NacionalidadDetailComponent } from './nacionalidad-detail.component';
import { NacionalidadUpdateComponent } from './nacionalidad-update.component';

@Injectable({ providedIn: 'root' })
export class NacionalidadResolve implements Resolve<INacionalidad> {
  constructor(private service: NacionalidadService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INacionalidad> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((nacionalidad: HttpResponse<Nacionalidad>) => {
          if (nacionalidad.body) {
            return of(nacionalidad.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Nacionalidad());
  }
}

export const nacionalidadRoute: Routes = [
  {
    path: '',
    component: NacionalidadComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.nacionalidad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NacionalidadDetailComponent,
    resolve: {
      nacionalidad: NacionalidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.nacionalidad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NacionalidadUpdateComponent,
    resolve: {
      nacionalidad: NacionalidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.nacionalidad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NacionalidadUpdateComponent,
    resolve: {
      nacionalidad: NacionalidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.nacionalidad.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
