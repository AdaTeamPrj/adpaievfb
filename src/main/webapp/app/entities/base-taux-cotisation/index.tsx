import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BaseTauxCotisation from './base-taux-cotisation';
import BaseTauxCotisationDetail from './base-taux-cotisation-detail';
import BaseTauxCotisationUpdate from './base-taux-cotisation-update';
import BaseTauxCotisationDeleteDialog from './base-taux-cotisation-delete-dialog';

const BaseTauxCotisationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BaseTauxCotisation />} />
    <Route path="new" element={<BaseTauxCotisationUpdate />} />
    <Route path=":id">
      <Route index element={<BaseTauxCotisationDetail />} />
      <Route path="edit" element={<BaseTauxCotisationUpdate />} />
      <Route path="delete" element={<BaseTauxCotisationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BaseTauxCotisationRoutes;
