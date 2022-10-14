import dayjs from 'dayjs';
import { IConventionCollective } from 'app/shared/model/convention-collective.model';
import { IEmployeur } from 'app/shared/model/employeur.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { ITypeContrat } from 'app/shared/model/type-contrat.model';
import { TypeForfait } from 'app/shared/model/enumerations/type-forfait.model';
import { TypeJourTravail } from 'app/shared/model/enumerations/type-jour-travail.model';

export interface IContrat {
  id?: number;
  salaireBase?: number;
  emploi?: string;
  dateArrive?: string;
  classification?: number;
  typeForfait?: TypeForfait;
  nbHeure?: number | null;
  typeJourTravail?: TypeJourTravail;
  dateDepart?: string | null;
  conventionCollective?: IConventionCollective | null;
  employeur?: IEmployeur | null;
  employee?: IEmployee | null;
  typeContrat?: ITypeContrat | null;
}

export const defaultValue: Readonly<IContrat> = {};
