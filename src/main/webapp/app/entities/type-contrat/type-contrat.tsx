import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITypeContrat } from 'app/shared/model/type-contrat.model';
import { getEntities } from './type-contrat.reducer';

export const TypeContrat = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const typeContratList = useAppSelector(state => state.typeContrat.entities);
  const loading = useAppSelector(state => state.typeContrat.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="type-contrat-heading" data-cy="TypeContratHeading">
        <Translate contentKey="adpaievfbApp.typeContrat.home.title">Type Contrats</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="adpaievfbApp.typeContrat.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/type-contrat/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="adpaievfbApp.typeContrat.home.createLabel">Create new Type Contrat</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {typeContratList && typeContratList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="adpaievfbApp.typeContrat.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.typeContrat.typeContrat">Type Contrat</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.typeContrat.cotisation">Cotisation</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {typeContratList.map((typeContrat, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/type-contrat/${typeContrat.id}`} color="link" size="sm">
                      {typeContrat.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`adpaievfbApp.TypeDeContrat.${typeContrat.typeContrat}`} />
                  </td>
                  <td>
                    {typeContrat.cotisations
                      ? typeContrat.cotisations.map((val, j) => (
                          <span key={j}>
                            <Link to={`/cotisation/${val.id}`}>{val.id}</Link>
                            {j === typeContrat.cotisations.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/type-contrat/${typeContrat.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/type-contrat/${typeContrat.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/type-contrat/${typeContrat.id}/delete`}
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
              <Translate contentKey="adpaievfbApp.typeContrat.home.notFound">No Type Contrats found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TypeContrat;
