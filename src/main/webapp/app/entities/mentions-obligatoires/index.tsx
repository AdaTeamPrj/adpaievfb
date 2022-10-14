import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MentionsObligatoires from './mentions-obligatoires';
import MentionsObligatoiresDetail from './mentions-obligatoires-detail';
import MentionsObligatoiresUpdate from './mentions-obligatoires-update';
import MentionsObligatoiresDeleteDialog from './mentions-obligatoires-delete-dialog';

const MentionsObligatoiresRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MentionsObligatoires />} />
    <Route path="new" element={<MentionsObligatoiresUpdate />} />
    <Route path=":id">
      <Route index element={<MentionsObligatoiresDetail />} />
      <Route path="edit" element={<MentionsObligatoiresUpdate />} />
      <Route path="delete" element={<MentionsObligatoiresDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MentionsObligatoiresRoutes;
