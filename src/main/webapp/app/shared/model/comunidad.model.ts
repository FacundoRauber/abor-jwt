import { IIntegrante } from 'app/shared/model/integrante.model';

export interface IComunidad {
  id?: number;
  nombre?: string;
  estado?: boolean;
  integrantes?: IIntegrante[];
}

export class Comunidad implements IComunidad {
  constructor(public id?: number, public nombre?: string, public estado?: boolean, public integrantes?: IIntegrante[]) {
    this.estado = this.estado || false;
  }
}
