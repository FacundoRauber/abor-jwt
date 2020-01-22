import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRelevamiento } from 'app/shared/model/relevamiento.model';

type EntityResponseType = HttpResponse<IRelevamiento>;
type EntityArrayResponseType = HttpResponse<IRelevamiento[]>;

@Injectable({ providedIn: 'root' })
export class RelevamientoService {
  public resourceUrl = SERVER_API_URL + 'api/relevamientos';

  constructor(protected http: HttpClient) {}

  create(relevamiento: IRelevamiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(relevamiento);
    return this.http
      .post<IRelevamiento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(relevamiento: IRelevamiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(relevamiento);
    return this.http
      .put<IRelevamiento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRelevamiento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRelevamiento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(relevamiento: IRelevamiento): IRelevamiento {
    const copy: IRelevamiento = Object.assign({}, relevamiento, {
      fecha: relevamiento.fecha && relevamiento.fecha.isValid() ? relevamiento.fecha.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((relevamiento: IRelevamiento) => {
        relevamiento.fecha = relevamiento.fecha ? moment(relevamiento.fecha) : undefined;
      });
    }
    return res;
  }
}
