import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/location">
        <Translate contentKey="global.menu.entities.location" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/heure-sup">
        <Translate contentKey="global.menu.entities.heureSup" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/employee">
        <Translate contentKey="global.menu.entities.employee" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/employeur">
        <Translate contentKey="global.menu.entities.employeur" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/job">
        <Translate contentKey="global.menu.entities.job" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/taux-d-imposition">
        <Translate contentKey="global.menu.entities.tauxDImposition" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/mentions-obligatoires">
        <Translate contentKey="global.menu.entities.mentionsObligatoires" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/conge">
        <Translate contentKey="global.menu.entities.conge" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/bonus">
        <Translate contentKey="global.menu.entities.bonus" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/contrat">
        <Translate contentKey="global.menu.entities.contrat" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/type-contrat">
        <Translate contentKey="global.menu.entities.typeContrat" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/fiche-de-paie">
        <Translate contentKey="global.menu.entities.ficheDePaie" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/convention-collective">
        <Translate contentKey="global.menu.entities.conventionCollective" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cotisation">
        <Translate contentKey="global.menu.entities.cotisation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/base-taux-cotisation">
        <Translate contentKey="global.menu.entities.baseTauxCotisation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/indicateurs">
        <Translate contentKey="global.menu.entities.indicateurs" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
