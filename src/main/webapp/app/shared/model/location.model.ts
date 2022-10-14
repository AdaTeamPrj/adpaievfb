import { IEmployee } from 'app/shared/model/employee.model';
import { IEmployeur } from 'app/shared/model/employeur.model';

export interface ILocation {
  id?: number;
  numeroRue?: string;
  nomVoie?: string;
  streetName?: string;
  postalCode?: string;
  city?: string;
  nomDepartement?: string;
  nomRegion?: string;
  employees?: IEmployee[] | null;
  employeurs?: IEmployeur[] | null;
}

export const defaultValue: Readonly<ILocation> = {};
