export interface INivelEducativo {
  id?: number;
  nombre?: string;
  estado?: boolean;
}

export class NivelEducativo implements INivelEducativo {
  constructor(public id?: number, public nombre?: string, public estado?: boolean) {
    this.estado = this.estado || false;
  }
}
