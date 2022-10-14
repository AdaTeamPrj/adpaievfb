import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mentions-obligatoires.reducer';

export const MentionsObligatoiresDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mentionsObligatoiresEntity = useAppSelector(state => state.mentionsObligatoires.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mentionsObligatoiresDetailsHeading">
          <Translate contentKey="adpaievfbApp.mentionsObligatoires.detail.title">MentionsObligatoires</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{mentionsObligatoiresEntity.id}</dd>
          <dt>
            <span id="mention">
              <Translate contentKey="adpaievfbApp.mentionsObligatoires.mention">Mention</Translate>
            </span>
          </dt>
          <dd>{mentionsObligatoiresEntity.mention}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="adpaievfbApp.mentionsObligatoires.description">Description</Translate>
            </span>
          </dt>
          <dd>{mentionsObligatoiresEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/mentions-obligatoires" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mentions-obligatoires/${mentionsObligatoiresEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MentionsObligatoiresDetail;
