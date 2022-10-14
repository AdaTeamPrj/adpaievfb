import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMentionsObligatoires } from 'app/shared/model/mentions-obligatoires.model';
import { getEntities } from './mentions-obligatoires.reducer';

export const MentionsObligatoires = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const mentionsObligatoiresList = useAppSelector(state => state.mentionsObligatoires.entities);
  const loading = useAppSelector(state => state.mentionsObligatoires.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="mentions-obligatoires-heading" data-cy="MentionsObligatoiresHeading">
        <Translate contentKey="adpaievfbApp.mentionsObligatoires.home.title">Mentions Obligatoires</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="adpaievfbApp.mentionsObligatoires.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/mentions-obligatoires/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="adpaievfbApp.mentionsObligatoires.home.createLabel">Create new Mentions Obligatoires</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {mentionsObligatoiresList && mentionsObligatoiresList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="adpaievfbApp.mentionsObligatoires.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.mentionsObligatoires.mention">Mention</Translate>
                </th>
                <th>
                  <Translate contentKey="adpaievfbApp.mentionsObligatoires.description">Description</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {mentionsObligatoiresList.map((mentionsObligatoires, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/mentions-obligatoires/${mentionsObligatoires.id}`} color="link" size="sm">
                      {mentionsObligatoires.id}
                    </Button>
                  </td>
                  <td>{mentionsObligatoires.mention}</td>
                  <td>{mentionsObligatoires.description}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/mentions-obligatoires/${mentionsObligatoires.id}`}
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
                        to={`/mentions-obligatoires/${mentionsObligatoires.id}/edit`}
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
                        to={`/mentions-obligatoires/${mentionsObligatoires.id}/delete`}
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
              <Translate contentKey="adpaievfbApp.mentionsObligatoires.home.notFound">No Mentions Obligatoires found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MentionsObligatoires;
