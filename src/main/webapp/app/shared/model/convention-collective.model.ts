import { IEmployeur } from 'app/shared/model/employeur.model';

export interface IConventionCollective {
  id?: number;
  idcc?: string;
  nom?: string;
  position?: number | null;
  coefficient?: number | null;
  valeurPoint?: number | null;
  baseFixe?: number | null;
  salaireMinimal?: number | null;
  employeurs?: IEmployeur[] | null;
}

export const defaultValue: Readonly<IConventionCollective> = {};
