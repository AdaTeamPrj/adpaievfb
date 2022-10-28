import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IIndicateurs } from 'app/shared/model/indicateurs.model';
import { getEntities } from './indicateurs.reducer';

export const Indicateurs = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const indicateursList = useAppSelector(state => state.indicateurs.entities);
  const loading = useAppSelector(state => state.indicateurs.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="indicateurs-heading" data-cy="IndicateursHeading">
        <Translate contentKey="adpaievfbApp.indicateurs.home.title">Indicateurs</Translate>
        <div className="d-flex justify-content-end"></div>
      </h2>
      <tbody>
        <br />
        <br />
        <img src="content/images/tdbgif.gif" width="1300" alt="React Adateam" className="img-fluid" /> <br />
        <br />
        <br />
        <ul className="body-link">
          <a href="#" title="Accueil">
            <br />
            <div className="item">Retour à l'accueil</div>
          </a>
        </ul>
        <br />
      </tbody>
    </div>
  );
};

export default Indicateurs;
