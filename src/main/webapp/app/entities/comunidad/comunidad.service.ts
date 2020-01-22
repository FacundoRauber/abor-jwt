import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IComunidad } from 'app/shared/model/comunidad.model';

type EntityResponseType = HttpResponse<IComunidad>;
type EntityArrayResponseType = HttpResponse<IComunidad[]>;

@Injectable({ providedIn: 'root' })
export class ComunidadService {
  public resourceUrl = SERVER_API_URL + 'api/comunidads';

  constructor(protected http: HttpClient) {}

  create(comunidad: IComunidad): Observable<EntityResponseType> {
    return this.http.post<IComunidad>(this.resourceUrl, comunidad, { observe: 'response' });
  }

  update(comunidad: IComunidad): Observable<EntityResponseType> {
    return this.http.put<IComunidad>(this.resourceUrl, comunidad, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IComunidad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComunidad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
