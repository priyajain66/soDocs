import { ISoDocs } from 'app/shared/model/so-docs.model';

export interface IShippingOrder {
  id?: number;
  soDocs?: ISoDocs[];
}

export class ShippingOrder implements IShippingOrder {
  constructor(public id?: number, public soDocs?: ISoDocs[]) {}
}
