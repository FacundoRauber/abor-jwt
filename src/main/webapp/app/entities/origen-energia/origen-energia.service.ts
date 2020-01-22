import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrigenEnergia } from 'app/shared/model/origen-energia.model';

type EntityResponseType = HttpResponse<IOrigenEnergia>;
type EntityArrayResponseType = HttpResponse<IOrigenEnergia[]>;

@Injectable({ providedIn: 'root' })
export class OrigenEnergiaService {
  public resourceUrl = SERVER_API_URL + 'api/origen-energias';

  constructor(protected http: HttpClient) {}

  create(origenEnergia: IOrigenEnergia): Observable<EntityResponseType> {
    return this.http.post<IOrigenEnergia>(this.resourceUrl, origenEnergia, { observe: 'response' });
  }

  update(origenEnergia: IOrigenEnergia): Observable<EntityResponseType> {
    return this.http.put<IOrigenEnergia>(this.resourceUrl, origenEnergia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrigenEnergia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrigenEnergia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
