import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRelevamiento, Relevamiento } from 'app/shared/model/relevamiento.model';
import { RelevamientoService } from './relevamiento.service';
import { RelevamientoComponent } from './relevamiento.component';
import { RelevamientoDetailComponent } from './relevamiento-detail.component';
import { RelevamientoUpdateComponent } from './relevamiento-update.component';

@Injectable({ providedIn: 'root' })
export class RelevamientoResolve implements Resolve<IRelevamiento> {
  constructor(private service: RelevamientoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRelevamiento> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((relevamiento: HttpResponse<Relevamiento>) => {
          if (relevamiento.body) {
            return of(relevamiento.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Relevamiento());
  }
}

export const relevamientoRoute: Routes = [
  {
    path: '',
    component: RelevamientoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.relevamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RelevamientoDetailComponent,
    resolve: {
      relevamiento: RelevamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.relevamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RelevamientoUpdateComponent,
    resolve: {
      relevamiento: RelevamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.relevamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RelevamientoUpdateComponent,
    resolve: {
      relevamiento: RelevamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.relevamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
