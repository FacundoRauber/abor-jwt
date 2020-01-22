import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrigenEnergia, OrigenEnergia } from 'app/shared/model/origen-energia.model';
import { OrigenEnergiaService } from './origen-energia.service';
import { OrigenEnergiaComponent } from './origen-energia.component';
import { OrigenEnergiaDetailComponent } from './origen-energia-detail.component';
import { OrigenEnergiaUpdateComponent } from './origen-energia-update.component';

@Injectable({ providedIn: 'root' })
export class OrigenEnergiaResolve implements Resolve<IOrigenEnergia> {
  constructor(private service: OrigenEnergiaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrigenEnergia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((origenEnergia: HttpResponse<OrigenEnergia>) => {
          if (origenEnergia.body) {
            return of(origenEnergia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrigenEnergia());
  }
}

export const origenEnergiaRoute: Routes = [
  {
    path: '',
    component: OrigenEnergiaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.origenEnergia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrigenEnergiaDetailComponent,
    resolve: {
      origenEnergia: OrigenEnergiaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.origenEnergia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrigenEnergiaUpdateComponent,
    resolve: {
      origenEnergia: OrigenEnergiaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.origenEnergia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrigenEnergiaUpdateComponent,
    resolve: {
      origenEnergia: OrigenEnergiaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.origenEnergia.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
