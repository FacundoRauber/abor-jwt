import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrigenAgua } from 'app/shared/model/origen-agua.model';

type EntityResponseType = HttpResponse<IOrigenAgua>;
type EntityArrayResponseType = HttpResponse<IOrigenAgua[]>;

@Injectable({ providedIn: 'root' })
export class OrigenAguaService {
  public resourceUrl = SERVER_API_URL + 'api/origen-aguas';

  constructor(protected http: HttpClient) {}

  create(origenAgua: IOrigenAgua): Observable<EntityResponseType> {
    return this.http.post<IOrigenAgua>(this.resourceUrl, origenAgua, { observe: 'response' });
  }

  update(origenAgua: IOrigenAgua): Observable<EntityResponseType> {
    return this.http.put<IOrigenAgua>(this.resourceUrl, origenAgua, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrigenAgua>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrigenAgua[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
