import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoPlanAsistencia, TipoPlanAsistencia } from 'app/shared/model/tipo-plan-asistencia.model';
import { TipoPlanAsistenciaService } from './tipo-plan-asistencia.service';
import { TipoPlanAsistenciaComponent } from './tipo-plan-asistencia.component';
import { TipoPlanAsistenciaDetailComponent } from './tipo-plan-asistencia-detail.component';
import { TipoPlanAsistenciaUpdateComponent } from './tipo-plan-asistencia-update.component';

@Injectable({ providedIn: 'root' })
export class TipoPlanAsistenciaResolve implements Resolve<ITipoPlanAsistencia> {
  constructor(private service: TipoPlanAsistenciaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoPlanAsistencia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoPlanAsistencia: HttpResponse<TipoPlanAsistencia>) => {
          if (tipoPlanAsistencia.body) {
            return of(tipoPlanAsistencia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoPlanAsistencia());
  }
}

export const tipoPlanAsistenciaRoute: Routes = [
  {
    path: '',
    component: TipoPlanAsistenciaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoPlanAsistencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoPlanAsistenciaDetailComponent,
    resolve: {
      tipoPlanAsistencia: TipoPlanAsistenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoPlanAsistencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoPlanAsistenciaUpdateComponent,
    resolve: {
      tipoPlanAsistencia: TipoPlanAsistenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoPlanAsistencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoPlanAsistenciaUpdateComponent,
    resolve: {
      tipoPlanAsistencia: TipoPlanAsistenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.tipoPlanAsistencia.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
