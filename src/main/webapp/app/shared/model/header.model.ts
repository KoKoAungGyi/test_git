export interface IHeader {
  id?: number;
  code?: string;
  description?: string;
}

export class Header implements IHeader {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
