import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoOcupacion } from 'app/shared/model/tipo-ocupacion.model';

type EntityResponseType = HttpResponse<ITipoOcupacion>;
type EntityArrayResponseType = HttpResponse<ITipoOcupacion[]>;

@Injectable({ providedIn: 'root' })
export class TipoOcupacionService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-ocupacions';

  constructor(protected http: HttpClient) {}

  create(tipoOcupacion: ITipoOcupacion): Observable<EntityResponseType> {
    return this.http.post<ITipoOcupacion>(this.resourceUrl, tipoOcupacion, { observe: 'response' });
  }

  update(tipoOcupacion: ITipoOcupacion): Observable<EntityResponseType> {
    return this.http.put<ITipoOcupacion>(this.resourceUrl, tipoOcupacion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoOcupacion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoOcupacion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
