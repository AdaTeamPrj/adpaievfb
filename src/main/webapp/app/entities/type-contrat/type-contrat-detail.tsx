import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './type-contrat.reducer';

export const TypeContratDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const typeContratEntity = useAppSelector(state => state.typeContrat.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="typeContratDetailsHeading">
          <Translate contentKey="adpaievfbApp.typeContrat.detail.title">TypeContrat</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{typeContratEntity.id}</dd>
          <dt>
            <span id="typeContrat">
              <Translate contentKey="adpaievfbApp.typeContrat.typeContrat">Type Contrat</Translate>
            </span>
          </dt>
          <dd>{typeContratEntity.typeContrat}</dd>
          <dt>
            <Translate contentKey="adpaievfbApp.typeContrat.cotisation">Cotisation</Translate>
          </dt>
          <dd>
            {typeContratEntity.cotisations
              ? typeContratEntity.cotisations.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {typeContratEntity.cotisations && i === typeContratEntity.cotisations.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/type-contrat" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/type-contrat/${typeContratEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TypeContratDetail;
