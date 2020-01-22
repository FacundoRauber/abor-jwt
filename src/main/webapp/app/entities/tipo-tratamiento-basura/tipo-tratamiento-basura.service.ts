import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoTratamientoBasura } from 'app/shared/model/tipo-tratamiento-basura.model';

type EntityResponseType = HttpResponse<ITipoTratamientoBasura>;
type EntityArrayResponseType = HttpResponse<ITipoTratamientoBasura[]>;

@Injectable({ providedIn: 'root' })
export class TipoTratamientoBasuraService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-tratamiento-basuras';

  constructor(protected http: HttpClient) {}

  create(tipoTratamientoBasura: ITipoTratamientoBasura): Observable<EntityResponseType> {
    return this.http.post<ITipoTratamientoBasura>(this.resourceUrl, tipoTratamientoBasura, { observe: 'response' });
  }

  update(tipoTratamientoBasura: ITipoTratamientoBasura): Observable<EntityResponseType> {
    return this.http.put<ITipoTratamientoBasura>(this.resourceUrl, tipoTratamientoBasura, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoTratamientoBasura>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoTratamientoBasura[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
