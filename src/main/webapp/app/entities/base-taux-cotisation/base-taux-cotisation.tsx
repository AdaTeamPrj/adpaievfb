import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBaseTauxCotisation } from 'app/shared/model/base-taux-cotisation.model';
import { getEntities } from './base-taux-cotisation.reducer';

export const BaseTauxCotisation = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const baseTauxCotisationList = useAppSelector(state => state.baseTauxCotisation.entities);
  const loading = useAppSelector(state => state.baseTauxCotisation.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="base-taux-cotisation-heading" data-cy="BaseTauxCotisationHeading">
        <Translate contentKey="adpaievfbApp.baseTauxCotisation.home.title">Base Taux Cotisations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="adpaievfbApp.baseTauxCotisation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/base-taux-cotisation/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="adpaievfbApp.baseTauxCotisation.home.createLabel">Create new Base Taux Cotisation</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {baseTauxCotisationList && baseTauxCotisationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="adpaievfbApp.baseTauxCotisation.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.baseTauxCotisation.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.baseTauxCotisation.montant">Montant</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {baseTauxCotisationList.map((baseTauxCotisation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/base-taux-cotisation/${baseTauxCotisation.id}`} color="link" size="sm">
                      {baseTauxCotisation.id}
                    </Button>
                  </td>
                  <td>{baseTauxCotisation.nom}</td>
                  <td>{baseTauxCotisation.montant}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/base-taux-cotisation/${baseTauxCotisation.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/base-taux-cotisation/${baseTauxCotisation.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/base-taux-cotisation/${baseTauxCotisation.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="adpaievfbApp.baseTauxCotisation.home.notFound">No Base Taux Cotisations found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BaseTauxCotisation;
