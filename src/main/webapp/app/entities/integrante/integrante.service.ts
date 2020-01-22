import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIntegrante } from 'app/shared/model/integrante.model';

type EntityResponseType = HttpResponse<IIntegrante>;
type EntityArrayResponseType = HttpResponse<IIntegrante[]>;

@Injectable({ providedIn: 'root' })
export class IntegranteService {
  public resourceUrl = SERVER_API_URL + 'api/integrantes';

  constructor(protected http: HttpClient) {}

  create(integrante: IIntegrante): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(integrante);
    return this.http
      .post<IIntegrante>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(integrante: IIntegrante): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(integrante);
    return this.http
      .put<IIntegrante>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIntegrante>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIntegrante[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(integrante: IIntegrante): IIntegrante {
    const copy: IIntegrante = Object.assign({}, integrante, {
      fechaNacimiento:
        integrante.fechaNacimiento && integrante.fechaNacimiento.isValid() ? integrante.fechaNacimiento.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaNacimiento = res.body.fechaNacimiento ? moment(res.body.fechaNacimiento) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((integrante: IIntegrante) => {
        integrante.fechaNacimiento = integrante.fechaNacimiento ? moment(integrante.fechaNacimiento) : undefined;
      });
    }
    return res;
  }
}
