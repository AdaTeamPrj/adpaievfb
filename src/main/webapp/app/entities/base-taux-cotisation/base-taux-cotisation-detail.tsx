import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './base-taux-cotisation.reducer';

export const BaseTauxCotisationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const baseTauxCotisationEntity = useAppSelector(state => state.baseTauxCotisation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="baseTauxCotisationDetailsHeading">
          <Translate contentKey="adpaievfbApp.baseTauxCotisation.detail.title">BaseTauxCotisation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{baseTauxCotisationEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="adpaievfbApp.baseTauxCotisation.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{baseTauxCotisationEntity.nom}</dd>
          <dt>
            <span id="montant">
              <Translate contentKey="adpaievfbApp.baseTauxCotisation.montant">Montant</Translate>
            </span>
          </dt>
          <dd>{baseTauxCotisationEntity.montant}</dd>
        </dl>
        <Button tag={Link} to="/base-taux-cotisation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/base-taux-cotisation/${baseTauxCotisationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BaseTauxCotisationDetail;
