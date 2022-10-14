export interface IBaseTauxCotisation {
  id?: number;
  nom?: string | null;
  montant?: number | null;
}

export const defaultValue: Readonly<IBaseTauxCotisation> = {};
