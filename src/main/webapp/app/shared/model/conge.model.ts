import dayjs from 'dayjs';
import { IContrat } from 'app/shared/model/contrat.model';
import { Decision } from 'app/shared/model/enumerations/decision.model';
import { TypeConge } from 'app/shared/model/enumerations/type-conge.model';

export interface IConge {
  id?: number;
  holdateStart?: string;
  holdateEnd?: string;
  nbCongeAcquis?: number | null;
  nbCongePris?: number | null;
  dateDemande?: string | null;
  decision?: Decision | null;
  dateReponse?: string | null;
  typeConge?: TypeConge | null;
  congePay?: number | null;
  contrat?: IContrat | null;
}

export const defaultValue: Readonly<IConge> = {};
