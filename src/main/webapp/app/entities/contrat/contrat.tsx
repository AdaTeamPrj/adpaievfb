import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContrat } from 'app/shared/model/contrat.model';
import { getEntities } from './contrat.reducer';

export const Contrat = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const contratList = useAppSelector(state => state.contrat.entities);
  const loading = useAppSelector(state => state.contrat.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="contrat-heading" data-cy="ContratHeading">
        <Translate contentKey="adpaievfbApp.contrat.home.title">Contrats</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="adpaievfbApp.contrat.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/contrat/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="adpaievfbApp.contrat.home.createLabel">Create new Contrat</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {contratList && contratList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="adpaievfbApp.contrat.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.contrat.salaireBase">Salaire Base</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.contrat.emploi">Emploi</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.contrat.dateArrive">Date Arrive</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.contrat.classification">Classification</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.contrat.typeForfait">Type Forfait</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.contrat.nbHeure">Nb Heure</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.contrat.typeJourTravail">Type Jour Travail</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.contrat.dateDepart">Date Depart</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.contrat.conventionCollective">Convention Collective</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.contrat.employeur">Employeur</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.contrat.employee">Employee</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.contrat.typeContrat">Type Contrat</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {contratList.map((contrat, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/contrat/${contrat.id}`} color="link" size="sm">
                      {contrat.id}
                    </Button>
                  </td>
                  <td>{contrat.salaireBase}</td>
                  <td>{contrat.emploi}</td>
                  <td>
                    {contrat.dateArrive ? <TextFormat type="date" value={contrat.dateArrive} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{contrat.classification}</td>
                  <td>
                    <Translate contentKey={`adpaievfbApp.TypeForfait.${contrat.typeForfait}`} />
                  </td>
                  <td>{contrat.nbHeure}</td>
                  <td>
                    <Translate contentKey={`adpaievfbApp.TypeJourTravail.${contrat.typeJourTravail}`} />
                  </td>
                  <td>
                    {contrat.dateDepart ? <TextFormat type="date" value={contrat.dateDepart} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {contrat.conventionCollective ? (
                      <Link to={`/convention-collective/${contrat.conventionCollective.id}`}>{contrat.conventionCollective.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{contrat.employeur ? <Link to={`/employeur/${contrat.employeur.id}`}>{contrat.employeur.id}</Link> : ''}</td>
                  <td>{contrat.employee ? <Link to={`/employee/${contrat.employee.id}`}>{contrat.employee.id}</Link> : ''}</td>
                  <td>{contrat.typeContrat ? <Link to={`/type-contrat/${contrat.typeContrat.id}`}>{contrat.typeContrat.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/contrat/${contrat.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/contrat/${contrat.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/contrat/${contrat.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="adpaievfbApp.contrat.home.notFound">No Contrats found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Contrat;
