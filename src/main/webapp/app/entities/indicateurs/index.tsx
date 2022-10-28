import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Indicateurs from './indicateurs';
import IndicateursDetail from './indicateurs-detail';

const IndicateursRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Indicateurs />} />
    <Route path=":id">
      <Route index element={<IndicateursDetail />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IndicateursRoutes;
