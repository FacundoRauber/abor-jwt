import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVinculoFamiliar } from 'app/shared/model/vinculo-familiar.model';

type EntityResponseType = HttpResponse<IVinculoFamiliar>;
type EntityArrayResponseType = HttpResponse<IVinculoFamiliar[]>;

@Injectable({ providedIn: 'root' })
export class VinculoFamiliarService {
  public resourceUrl = SERVER_API_URL + 'api/vinculo-familiars';

  constructor(protected http: HttpClient) {}

  create(vinculoFamiliar: IVinculoFamiliar): Observable<EntityResponseType> {
    return this.http.post<IVinculoFamiliar>(this.resourceUrl, vinculoFamiliar, { observe: 'response' });
  }

  update(vinculoFamiliar: IVinculoFamiliar): Observable<EntityResponseType> {
    return this.http.put<IVinculoFamiliar>(this.resourceUrl, vinculoFamiliar, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVinculoFamiliar>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVinculoFamiliar[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
