export interface ITipoPlanAsistencia {
  id?: number;
  nombre?: string;
  estado?: boolean;
}

export class TipoPlanAsistencia implements ITipoPlanAsistencia {
  constructor(public id?: number, public nombre?: string, public estado?: boolean) {
    this.estado = this.estado || false;
  }
}
