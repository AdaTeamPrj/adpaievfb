import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Location from './location';
import HeureSup from './heure-sup';
import Employee from './employee';
import Employeur from './employeur';
import Job from './job';
import TauxDImposition from './taux-d-imposition';
import MentionsObligatoires from './mentions-obligatoires';
import Conge from './conge';
import Bonus from './bonus';
import Contrat from './contrat';
import TypeContrat from './type-contrat';
import FicheDePaie from './fiche-de-paie';
import ConventionCollective from './convention-collective';
import Cotisation from './cotisation';
import BaseTauxCotisation from './base-taux-cotisation';
import Indicateurs from './indicateurs';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="location/*" element={<Location />} />
        <Route path="heure-sup/*" element={<HeureSup />} />
        <Route path="employee/*" element={<Employee />} />
        <Route path="employeur/*" element={<Employeur />} />
        <Route path="job/*" element={<Job />} />
        <Route path="taux-d-imposition/*" element={<TauxDImposition />} />
        <Route path="mentions-obligatoires/*" element={<MentionsObligatoires />} />
        <Route path="conge/*" element={<Conge />} />
        <Route path="bonus/*" element={<Bonus />} />
        <Route path="contrat/*" element={<Contrat />} />
        <Route path="type-contrat/*" element={<TypeContrat />} />
        <Route path="fiche-de-paie/*" element={<FicheDePaie />} />
        <Route path="convention-collective/*" element={<ConventionCollective />} />
        <Route path="cotisation/*" element={<Cotisation />} />
        <Route path="base-taux-cotisation/*" element={<BaseTauxCotisation />} />
        <Route path="indicateurs/*" element={<Indicateurs />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
