export interface ITipoVivienda {
  id?: number;
  nombre?: string;
  estado?: boolean;
}

export class TipoVivienda implements ITipoVivienda {
  constructor(public id?: number, public nombre?: string, public estado?: boolean) {
    this.estado = this.estado || false;
  }
}
