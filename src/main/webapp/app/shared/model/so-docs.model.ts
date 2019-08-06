export interface ISoDocs {
  id?: number;
  fileName?: string;
  uploadFileContentType?: string;
  uploadFile?: any;
  uploadFileContentType?: string;
  privateMode?: boolean;
  shippingOrderId?: number;
  docsTypeId?: number;
}

export class SoDocs implements ISoDocs {
  constructor(
    public id?: number,
    public fileName?: string,
    public uploadFileContentType?: string,
    public uploadFile?: any,
    public uploadFileContentType?: string,
    public privateMode?: boolean,
    public shippingOrderId?: number,
    public docsTypeId?: number
  ) {
    this.privateMode = this.privateMode || false;
  }
}
