import { IRelevamiento } from 'app/shared/model/relevamiento.model';

export interface ITipoServicio {
  id?: number;
  nombre?: string;
  estado?: boolean;
  relevamientos?: IRelevamiento[];
}

export class TipoServicio implements ITipoServicio {
  constructor(public id?: number, public nombre?: string, public estado?: boolean, public relevamientos?: IRelevamiento[]) {
    this.estado = this.estado || false;
  }
}
