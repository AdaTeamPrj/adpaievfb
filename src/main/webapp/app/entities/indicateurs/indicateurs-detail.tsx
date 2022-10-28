import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './indicateurs.reducer';

export const IndicateursDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const indicateursEntity = useAppSelector(state => state.indicateurs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="indicateursDetailsHeading">
          <Translate contentKey="adpaievfbApp.indicateurs.detail.title">Indicateurs</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{indicateursEntity.id}</dd>
          <dt>
            <span id="gestiondepaie">
              <Translate contentKey="adpaievfbApp.indicateurs.gestiondepaie">Gestiondepaie</Translate>
            </span>
          </dt>
          <dd>{indicateursEntity.gestiondepaie}</dd>
        </dl>
        <Button tag={Link} to="/indicateurs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/indicateurs/${indicateursEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default IndicateursDetail;
