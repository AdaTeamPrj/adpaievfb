import location from 'app/entities/location/location.reducer';
import heureSup from 'app/entities/heure-sup/heure-sup.reducer';
import employee from 'app/entities/employee/employee.reducer';
import employeur from 'app/entities/employeur/employeur.reducer';
import job from 'app/entities/job/job.reducer';
import tauxDImposition from 'app/entities/taux-d-imposition/taux-d-imposition.reducer';
import mentionsObligatoires from 'app/entities/mentions-obligatoires/mentions-obligatoires.reducer';
import conge from 'app/entities/conge/conge.reducer';
import bonus from 'app/entities/bonus/bonus.reducer';
import contrat from 'app/entities/contrat/contrat.reducer';
import typeContrat from 'app/entities/type-contrat/type-contrat.reducer';
import ficheDePaie from 'app/entities/fiche-de-paie/fiche-de-paie.reducer';
import conventionCollective from 'app/entities/convention-collective/convention-collective.reducer';
import cotisation from 'app/entities/cotisation/cotisation.reducer';
import baseTauxCotisation from 'app/entities/base-taux-cotisation/base-taux-cotisation.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  location,
  heureSup,
  employee,
  employeur,
  job,
  tauxDImposition,
  mentionsObligatoires,
  conge,
  bonus,
  contrat,
  typeContrat,
  ficheDePaie,
  conventionCollective,
  cotisation,
  baseTauxCotisation,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
