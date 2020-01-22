import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INivelEducativo, NivelEducativo } from 'app/shared/model/nivel-educativo.model';
import { NivelEducativoService } from './nivel-educativo.service';
import { NivelEducativoComponent } from './nivel-educativo.component';
import { NivelEducativoDetailComponent } from './nivel-educativo-detail.component';
import { NivelEducativoUpdateComponent } from './nivel-educativo-update.component';

@Injectable({ providedIn: 'root' })
export class NivelEducativoResolve implements Resolve<INivelEducativo> {
  constructor(private service: NivelEducativoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INivelEducativo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((nivelEducativo: HttpResponse<NivelEducativo>) => {
          if (nivelEducativo.body) {
            return of(nivelEducativo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NivelEducativo());
  }
}

export const nivelEducativoRoute: Routes = [
  {
    path: '',
    component: NivelEducativoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.nivelEducativo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NivelEducativoDetailComponent,
    resolve: {
      nivelEducativo: NivelEducativoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.nivelEducativo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NivelEducativoUpdateComponent,
    resolve: {
      nivelEducativo: NivelEducativoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.nivelEducativo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NivelEducativoUpdateComponent,
    resolve: {
      nivelEducativo: NivelEducativoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.nivelEducativo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
