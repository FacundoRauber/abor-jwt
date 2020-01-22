import { Moment } from 'moment';
import { IIntegrante } from 'app/shared/model/integrante.model';
import { IOrigenEnergia } from 'app/shared/model/origen-energia.model';
import { IOrigenAgua } from 'app/shared/model/origen-agua.model';
import { ITipoServicio } from 'app/shared/model/tipo-servicio.model';

export interface IRelevamiento {
  id?: number;
  fecha?: Moment;
  escuela?: boolean;
  puestoSalud?: boolean;
  estado?: boolean;
  integrante?: IIntegrante;
  origenenergia?: IOrigenEnergia;
  origenagua?: IOrigenAgua;
  tiposervicio?: ITipoServicio;
}

export class Relevamiento implements IRelevamiento {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public escuela?: boolean,
    public puestoSalud?: boolean,
    public estado?: boolean,
    public integrante?: IIntegrante,
    public origenenergia?: IOrigenEnergia,
    public origenagua?: IOrigenAgua,
    public tiposervicio?: ITipoServicio
  ) {
    this.escuela = this.escuela || false;
    this.puestoSalud = this.puestoSalud || false;
    this.estado = this.estado || false;
  }
}
