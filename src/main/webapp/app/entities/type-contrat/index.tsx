import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TypeContrat from './type-contrat';
import TypeContratDetail from './type-contrat-detail';
import TypeContratUpdate from './type-contrat-update';
import TypeContratDeleteDialog from './type-contrat-delete-dialog';

const TypeContratRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TypeContrat />} />
    <Route path="new" element={<TypeContratUpdate />} />
    <Route path=":id">
      <Route index element={<TypeContratDetail />} />
      <Route path="edit" element={<TypeContratUpdate />} />
      <Route path="delete" element={<TypeContratDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TypeContratRoutes;
