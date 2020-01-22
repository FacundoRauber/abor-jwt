export interface INacionalidad {
  id?: number;
  nombre?: string;
  estado?: boolean;
}

export class Nacionalidad implements INacionalidad {
  constructor(public id?: number, public nombre?: string, public estado?: boolean) {
    this.estado = this.estado || false;
  }
}
