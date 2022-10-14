import dayjs from 'dayjs';
import { IContrat } from 'app/shared/model/contrat.model';
import { IEmployeur } from 'app/shared/model/employeur.model';
import { ITauxDImposition } from 'app/shared/model/taux-d-imposition.model';
import { ICotisation } from 'app/shared/model/cotisation.model';
import { IMentionsObligatoires } from 'app/shared/model/mentions-obligatoires.model';

export interface IFicheDePaie {
  id?: number;
  salaireBrut?: number | null;
  startDate?: string | null;
  endDate?: string | null;
  datePaiement?: string | null;
  salaireNet?: number | null;
  montantNetAvantImpots?: number | null;
  proFees?: number | null;
  deductions?: number | null;
  contrat?: IContrat | null;
  employeur?: IEmployeur | null;
  imposition?: ITauxDImposition | null;
  cotisations?: ICotisation[] | null;
  mentionsObligatoires?: IMentionsObligatoires[] | null;
}

export const defaultValue: Readonly<IFicheDePaie> = {};
