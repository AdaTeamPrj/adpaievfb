import { IJob } from 'app/shared/model/job.model';
import { ILocation } from 'app/shared/model/location.model';

export interface IEmployee {
  id?: number;
  firstName?: string;
  lastName?: string;
  numeroSecuriteSociale?: string | null;
  qualification?: string;
  tauxImposition?: number | null;
  employee?: IEmployee | null;
  jobs?: IJob[] | null;
  locations?: ILocation[] | null;
}

export const defaultValue: Readonly<IEmployee> = {};
