import dayjs from 'dayjs';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IHeureSup {
  id?: number;
  date?: string | null;
  nbHeure?: number | null;
  employee?: IEmployee | null;
}

export const defaultValue: Readonly<IHeureSup> = {};
