import { IContrat } from 'app/shared/model/contrat.model';
import { ICotisation } from 'app/shared/model/cotisation.model';
import { TypeDeContrat } from 'app/shared/model/enumerations/type-de-contrat.model';

export interface ITypeContrat {
  id?: number;
  typeContrat?: TypeDeContrat;
  contrats?: IContrat[] | null;
  cotisations?: ICotisation[] | null;
}

export const defaultValue: Readonly<ITypeContrat> = {};
