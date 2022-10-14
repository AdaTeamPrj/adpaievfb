import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './location.reducer';

export const LocationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locationEntity = useAppSelector(state => state.location.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locationDetailsHeading">
          <Translate contentKey="adpaievfbApp.location.detail.title">Location</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{locationEntity.id}</dd>
          <dt>
            <span id="numeroRue">
              <Translate contentKey="adpaievfbApp.location.numeroRue">Numero Rue</Translate>
            </span>
          </dt>
          <dd>{locationEntity.numeroRue}</dd>
          <dt>
            <span id="nomVoie">
              <Translate contentKey="adpaievfbApp.location.nomVoie">Nom Voie</Translate>
            </span>
          </dt>
          <dd>{locationEntity.nomVoie}</dd>
          <dt>
            <span id="streetName">
              <Translate contentKey="adpaievfbApp.location.streetName">Street Name</Translate>
            </span>
          </dt>
          <dd>{locationEntity.streetName}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="adpaievfbApp.location.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{locationEntity.postalCode}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="adpaievfbApp.location.city">City</Translate>
            </span>
          </dt>
          <dd>{locationEntity.city}</dd>
          <dt>
            <span id="nomDepartement">
              <Translate contentKey="adpaievfbApp.location.nomDepartement">Nom Departement</Translate>
            </span>
          </dt>
          <dd>{locationEntity.nomDepartement}</dd>
          <dt>
            <span id="nomRegion">
              <Translate contentKey="adpaievfbApp.location.nomRegion">Nom Region</Translate>
            </span>
          </dt>
          <dd>{locationEntity.nomRegion}</dd>
          <dt>
            <Translate contentKey="adpaievfbApp.location.employee">Employee</Translate>
          </dt>
          <dd>
            {locationEntity.employees
              ? locationEntity.employees.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {locationEntity.employees && i === locationEntity.employees.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="adpaievfbApp.location.employeur">Employeur</Translate>
          </dt>
          <dd>
            {locationEntity.employeurs
              ? locationEntity.employeurs.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {locationEntity.employeurs && i === locationEntity.employeurs.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/location" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/location/${locationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocationDetail;
