import { IFicheDePaie } from 'app/shared/model/fiche-de-paie.model';

export interface IMentionsObligatoires {
  id?: number;
  mention?: string;
  description?: string;
  ficheDePaies?: IFicheDePaie[] | null;
}

export const defaultValue: Readonly<IMentionsObligatoires> = {};
