import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './heure-sup.reducer';

export const HeureSupDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const heureSupEntity = useAppSelector(state => state.heureSup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="heureSupDetailsHeading">
          <Translate contentKey="adpaievfbApp.heureSup.detail.title">HeureSup</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{heureSupEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="adpaievfbApp.heureSup.date">Date</Translate>
            </span>
          </dt>
          <dd>{heureSupEntity.date ? <TextFormat value={heureSupEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="nbHeure">
              <Translate contentKey="adpaievfbApp.heureSup.nbHeure">Nb Heure</Translate>
            </span>
          </dt>
          <dd>{heureSupEntity.nbHeure}</dd>
          <dt>
            <Translate contentKey="adpaievfbApp.heureSup.employee">Employee</Translate>
          </dt>
          <dd>{heureSupEntity.employee ? heureSupEntity.employee.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/heure-sup" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/heure-sup/${heureSupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HeureSupDetail;
