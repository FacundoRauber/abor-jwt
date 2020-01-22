import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVinculoFamiliar, VinculoFamiliar } from 'app/shared/model/vinculo-familiar.model';
import { VinculoFamiliarService } from './vinculo-familiar.service';
import { VinculoFamiliarComponent } from './vinculo-familiar.component';
import { VinculoFamiliarDetailComponent } from './vinculo-familiar-detail.component';
import { VinculoFamiliarUpdateComponent } from './vinculo-familiar-update.component';

@Injectable({ providedIn: 'root' })
export class VinculoFamiliarResolve implements Resolve<IVinculoFamiliar> {
  constructor(private service: VinculoFamiliarService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVinculoFamiliar> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vinculoFamiliar: HttpResponse<VinculoFamiliar>) => {
          if (vinculoFamiliar.body) {
            return of(vinculoFamiliar.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VinculoFamiliar());
  }
}

export const vinculoFamiliarRoute: Routes = [
  {
    path: '',
    component: VinculoFamiliarComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.vinculoFamiliar.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VinculoFamiliarDetailComponent,
    resolve: {
      vinculoFamiliar: VinculoFamiliarResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.vinculoFamiliar.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VinculoFamiliarUpdateComponent,
    resolve: {
      vinculoFamiliar: VinculoFamiliarResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.vinculoFamiliar.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VinculoFamiliarUpdateComponent,
    resolve: {
      vinculoFamiliar: VinculoFamiliarResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testmono04App.vinculoFamiliar.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
