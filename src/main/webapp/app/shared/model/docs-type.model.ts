import { ISoDocs } from 'app/shared/model/so-docs.model';

export interface IDocsType {
  id?: number;
  name?: string;
  soDocs?: ISoDocs[];
}

export class DocsType implements IDocsType {
  constructor(public id?: number, public name?: string, public soDocs?: ISoDocs[]) {}
}
