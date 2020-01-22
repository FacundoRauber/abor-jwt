import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INacionalidad } from 'app/shared/model/nacionalidad.model';

type EntityResponseType = HttpResponse<INacionalidad>;
type EntityArrayResponseType = HttpResponse<INacionalidad[]>;

@Injectable({ providedIn: 'root' })
export class NacionalidadService {
  public resourceUrl = SERVER_API_URL + 'api/nacionalidads';

  constructor(protected http: HttpClient) {}

  create(nacionalidad: INacionalidad): Observable<EntityResponseType> {
    return this.http.post<INacionalidad>(this.resourceUrl, nacionalidad, { observe: 'response' });
  }

  update(nacionalidad: INacionalidad): Observable<EntityResponseType> {
    return this.http.put<INacionalidad>(this.resourceUrl, nacionalidad, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INacionalidad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INacionalidad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
