import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoPlanAsistencia } from 'app/shared/model/tipo-plan-asistencia.model';

type EntityResponseType = HttpResponse<ITipoPlanAsistencia>;
type EntityArrayResponseType = HttpResponse<ITipoPlanAsistencia[]>;

@Injectable({ providedIn: 'root' })
export class TipoPlanAsistenciaService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-plan-asistencias';

  constructor(protected http: HttpClient) {}

  create(tipoPlanAsistencia: ITipoPlanAsistencia): Observable<EntityResponseType> {
    return this.http.post<ITipoPlanAsistencia>(this.resourceUrl, tipoPlanAsistencia, { observe: 'response' });
  }

  update(tipoPlanAsistencia: ITipoPlanAsistencia): Observable<EntityResponseType> {
    return this.http.put<ITipoPlanAsistencia>(this.resourceUrl, tipoPlanAsistencia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoPlanAsistencia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoPlanAsistencia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
