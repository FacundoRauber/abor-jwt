import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INivelEducativo } from 'app/shared/model/nivel-educativo.model';

type EntityResponseType = HttpResponse<INivelEducativo>;
type EntityArrayResponseType = HttpResponse<INivelEducativo[]>;

@Injectable({ providedIn: 'root' })
export class NivelEducativoService {
  public resourceUrl = SERVER_API_URL + 'api/nivel-educativos';

  constructor(protected http: HttpClient) {}

  create(nivelEducativo: INivelEducativo): Observable<EntityResponseType> {
    return this.http.post<INivelEducativo>(this.resourceUrl, nivelEducativo, { observe: 'response' });
  }

  update(nivelEducativo: INivelEducativo): Observable<EntityResponseType> {
    return this.http.put<INivelEducativo>(this.resourceUrl, nivelEducativo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INivelEducativo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INivelEducativo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
