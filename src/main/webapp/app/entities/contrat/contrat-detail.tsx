import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contrat.reducer';

export const ContratDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const contratEntity = useAppSelector(state => state.contrat.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contratDetailsHeading">
          <Translate contentKey="adpaievfbApp.contrat.detail.title">Contrat</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{contratEntity.id}</dd>
          <dt>
            <span id="salaireBase">
              <Translate contentKey="adpaievfbApp.contrat.salaireBase">Salaire Base</Translate>
            </span>
          </dt>
          <dd>{contratEntity.salaireBase}</dd>
          <dt>
            <span id="emploi">
              <Translate contentKey="adpaievfbApp.contrat.emploi">Emploi</Translate>
            </span>
          </dt>
          <dd>{contratEntity.emploi}</dd>
          <dt>
            <span id="dateArrive">
              <Translate contentKey="adpaievfbApp.contrat.dateArrive">Date Arrive</Translate>
            </span>
          </dt>
          <dd>
            {contratEntity.dateArrive ? <TextFormat value={contratEntity.dateArrive} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="classification">
              <Translate contentKey="adpaievfbApp.contrat.classification">Classification</Translate>
            </span>
          </dt>
          <dd>{contratEntity.classification}</dd>
          <dt>
            <span id="typeForfait">
              <Translate contentKey="adpaievfbApp.contrat.typeForfait">Type Forfait</Translate>
            </span>
          </dt>
          <dd>{contratEntity.typeForfait}</dd>
          <dt>
            <span id="nbHeure">
              <Translate contentKey="adpaievfbApp.contrat.nbHeure">Nb Heure</Translate>
            </span>
          </dt>
          <dd>{contratEntity.nbHeure}</dd>
          <dt>
            <span id="typeJourTravail">
              <Translate contentKey="adpaievfbApp.contrat.typeJourTravail">Type Jour Travail</Translate>
            </span>
          </dt>
          <dd>{contratEntity.typeJourTravail}</dd>
          <dt>
            <span id="dateDepart">
              <Translate contentKey="adpaievfbApp.contrat.dateDepart">Date Depart</Translate>
            </span>
          </dt>
          <dd>
            {contratEntity.dateDepart ? <TextFormat value={contratEntity.dateDepart} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="adpaievfbApp.contrat.conventionCollective">Convention Collective</Translate>
          </dt>
          <dd>{contratEntity.conventionCollective ? contratEntity.conventionCollective.id : ''}</dd>
          <dt>
            <Translate contentKey="adpaievfbApp.contrat.employeur">Employeur</Translate>
          </dt>
          <dd>{contratEntity.employeur ? contratEntity.employeur.id : ''}</dd>
          <dt>
            <Translate contentKey="adpaievfbApp.contrat.employee">Employee</Translate>
          </dt>
          <dd>{contratEntity.employee ? contratEntity.employee.id : ''}</dd>
          <dt>
            <Translate contentKey="adpaievfbApp.contrat.typeContrat">Type Contrat</Translate>
          </dt>
          <dd>{contratEntity.typeContrat ? contratEntity.typeContrat.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/contrat" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contrat/${contratEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContratDetail;
