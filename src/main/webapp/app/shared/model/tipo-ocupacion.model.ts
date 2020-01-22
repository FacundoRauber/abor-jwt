export interface ITipoOcupacion {
  id?: number;
  nombre?: string;
  estado?: boolean;
}

export class TipoOcupacion implements ITipoOcupacion {
  constructor(public id?: number, public nombre?: string, public estado?: boolean) {
    this.estado = this.estado || false;
  }
}
