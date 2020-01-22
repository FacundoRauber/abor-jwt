export interface IVinculoFamiliar {
  id?: number;
  nombre?: string;
  estado?: boolean;
}

export class VinculoFamiliar implements IVinculoFamiliar {
  constructor(public id?: number, public nombre?: string, public estado?: boolean) {
    this.estado = this.estado || false;
  }
}
