import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHeader, Header } from 'app/shared/model/header.model';
import { HeaderService } from './header.service';
import { HeaderComponent } from './header.component';
import { HeaderDetailComponent } from './header-detail.component';

@Injectable({ providedIn: 'root' })
export class HeaderResolve implements Resolve<IHeader> {
  constructor(private service: HeaderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHeader> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((header: HttpResponse<Header>) => {
          if (header.body) {
            return of(header.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Header());
  }
}

export const headerRoute: Routes = [
  {
    path: '',
    component: HeaderComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'demoApp.header.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HeaderDetailComponent,
    resolve: {
      header: HeaderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'demoApp.header.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
