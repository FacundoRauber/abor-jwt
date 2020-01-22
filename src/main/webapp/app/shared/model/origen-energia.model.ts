import { IRelevamiento } from 'app/shared/model/relevamiento.model';

export interface IOrigenEnergia {
  id?: number;
  nombre?: string;
  estado?: boolean;
  relevamientos?: IRelevamiento[];
}

export class OrigenEnergia implements IOrigenEnergia {
  constructor(public id?: number, public nombre?: string, public estado?: boolean, public relevamientos?: IRelevamiento[]) {
    this.estado = this.estado || false;
  }
}
