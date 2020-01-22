export interface ITipoTratamientoBasura {
  id?: number;
  nombre?: string;
  estado?: boolean;
}

export class TipoTratamientoBasura implements ITipoTratamientoBasura {
  constructor(public id?: number, public nombre?: string, public estado?: boolean) {
    this.estado = this.estado || false;
  }
}
