import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HeureSup from './heure-sup';
import HeureSupDetail from './heure-sup-detail';
import HeureSupUpdate from './heure-sup-update';
import HeureSupDeleteDialog from './heure-sup-delete-dialog';

const HeureSupRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HeureSup />} />
    <Route path="new" element={<HeureSupUpdate />} />
    <Route path=":id">
      <Route index element={<HeureSupDetail />} />
      <Route path="edit" element={<HeureSupUpdate />} />
      <Route path="delete" element={<HeureSupDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HeureSupRoutes;
