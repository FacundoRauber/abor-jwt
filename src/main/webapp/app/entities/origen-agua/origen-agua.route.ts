import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrigenAgua, OrigenAgua } from 'app/shared/model/origen-agua.model';
import { OrigenAguaService } from './origen-agua.service';
import { OrigenAguaComponent } from './origen-agua.component';
import { OrigenAguaDetailComponent } from './origen-agua-detail.component';
import { OrigenAguaUpdateComponent } from './origen-agua-update.component';

@Injectable({ providedIn: 'root' })
export class OrigenAguaResolve implements Resolve<IOrigenAgua> {
  constructor(private service: OrigenAguaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrigenAgua> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((origenAgua: HttpResponse<OrigenAgua>) => {
          if (origenAgua.body) {
            return of(origenAgua.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrigenAgua());
  }
}

export const origenAguaRoute: Routes = [
  {
    path: '',
    component: OrigenAguaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.origenAgua.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrigenAguaDetailComponent,
    resolve: {
      origenAgua: OrigenAguaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.origenAgua.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrigenAguaUpdateComponent,
    resolve: {
      origenAgua: OrigenAguaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.origenAgua.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrigenAguaUpdateComponent,
    resolve: {
      origenAgua: OrigenAguaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.origenAgua.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
