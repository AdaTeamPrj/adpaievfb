import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHeureSup } from 'app/shared/model/heure-sup.model';
import { getEntities } from './heure-sup.reducer';

export const HeureSup = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const heureSupList = useAppSelector(state => state.heureSup.entities);
  const loading = useAppSelector(state => state.heureSup.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="heure-sup-heading" data-cy="HeureSupHeading">
        <Translate contentKey="adpaievfbApp.heureSup.home.title">Heure Sups</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="adpaievfbApp.heureSup.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/heure-sup/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="adpaievfbApp.heureSup.home.createLabel">Create new Heure Sup</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {heureSupList && heureSupList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="adpaievfbApp.heureSup.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.heureSup.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.heureSup.nbHeure">Nb Heure</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.heureSup.employee">Employee</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {heureSupList.map((heureSup, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/heure-sup/${heureSup.id}`} color="link" size="sm">
                      {heureSup.id}
                    </Button>
                  </td>
                  <td>{heureSup.date ? <TextFormat type="date" value={heureSup.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{heureSup.nbHeure}</td>
                  <td>{heureSup.employee ? <Link to={`/employee/${heureSup.employee.id}`}>{heureSup.employee.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/heure-sup/${heureSup.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/heure-sup/${heureSup.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/heure-sup/${heureSup.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="adpaievfbApp.heureSup.home.notFound">No Heure Sups found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default HeureSup;
