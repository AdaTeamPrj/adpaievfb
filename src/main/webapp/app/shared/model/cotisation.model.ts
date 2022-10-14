import dayjs from 'dayjs';
import { IBaseTauxCotisation } from 'app/shared/model/base-taux-cotisation.model';
import { IFicheDePaie } from 'app/shared/model/fiche-de-paie.model';
import { ITypeContrat } from 'app/shared/model/type-contrat.model';
import { Categorie } from 'app/shared/model/enumerations/categorie.model';

export interface ICotisation {
  id?: number;
  name?: string;
  famille?: Categorie;
  taux?: number;
  startDate?: string;
  endDate?: string | null;
  actuel?: boolean;
  multiplicateurMin?: number | null;
  multiplicateurMax?: number | null;
  baseTauxCotisation?: IBaseTauxCotisation | null;
  ficheDePaies?: IFicheDePaie[] | null;
  typeContrats?: ITypeContrat[] | null;
}

export const defaultValue: Readonly<ICotisation> = {
  actuel: false,
};
