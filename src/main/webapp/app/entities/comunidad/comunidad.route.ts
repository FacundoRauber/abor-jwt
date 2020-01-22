import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IComunidad, Comunidad } from 'app/shared/model/comunidad.model';
import { ComunidadService } from './comunidad.service';
import { ComunidadComponent } from './comunidad.component';
import { ComunidadDetailComponent } from './comunidad-detail.component';
import { ComunidadUpdateComponent } from './comunidad-update.component';

@Injectable({ providedIn: 'root' })
export class ComunidadResolve implements Resolve<IComunidad> {
  constructor(private service: ComunidadService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IComunidad> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((comunidad: HttpResponse<Comunidad>) => {
          if (comunidad.body) {
            return of(comunidad.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Comunidad());
  }
}

export const comunidadRoute: Routes = [
  {
    path: '',
    component: ComunidadComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.comunidad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ComunidadDetailComponent,
    resolve: {
      comunidad: ComunidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.comunidad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ComunidadUpdateComponent,
    resolve: {
      comunidad: ComunidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.comunidad.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ComunidadUpdateComponent,
    resolve: {
      comunidad: ComunidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.comunidad.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
