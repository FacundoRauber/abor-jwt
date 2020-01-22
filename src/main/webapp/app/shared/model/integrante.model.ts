import { Moment } from 'moment';
import { IRelevamiento } from 'app/shared/model/relevamiento.model';
import { IComunidad } from 'app/shared/model/comunidad.model';
import { Sexo } from 'app/shared/model/enumerations/sexo.model';

export interface IIntegrante {
  id?: number;
  dni?: number;
  apelllido?: string;
  nombre?: string;
  fechaNacimiento?: Moment;
  edad?: number;
  sexo?: Sexo;
  estado?: boolean;
  relevamientos?: IRelevamiento[];
  comunidad?: IComunidad;
}

export class Integrante implements IIntegrante {
  constructor(
    public id?: number,
    public dni?: number,
    public apelllido?: string,
    public nombre?: string,
    public fechaNacimiento?: Moment,
    public edad?: number,
    public sexo?: Sexo,
    public estado?: boolean,
    public relevamientos?: IRelevamiento[],
    public comunidad?: IComunidad
  ) {
    this.estado = this.estado || false;
  }
}
