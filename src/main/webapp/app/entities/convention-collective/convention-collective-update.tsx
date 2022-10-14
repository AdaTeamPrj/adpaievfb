import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployeur } from 'app/shared/model/employeur.model';
import { getEntities as getEmployeurs } from 'app/entities/employeur/employeur.reducer';
import { IConventionCollective } from 'app/shared/model/convention-collective.model';
import { getEntity, updateEntity, createEntity, reset } from './convention-collective.reducer';

export const ConventionCollectiveUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const employeurs = useAppSelector(state => state.employeur.entities);
  const conventionCollectiveEntity = useAppSelector(state => state.conventionCollective.entity);
  const loading = useAppSelector(state => state.conventionCollective.loading);
  const updating = useAppSelector(state => state.conventionCollective.updating);
  const updateSuccess = useAppSelector(state => state.conventionCollective.updateSuccess);

  const handleClose = () => {
    navigate('/convention-collective');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEmployeurs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...conventionCollectiveEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...conventionCollectiveEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="adpaievfbApp.conventionCollective.home.createOrEditLabel" data-cy="ConventionCollectiveCreateUpdateHeading">
            <Translate contentKey="adpaievfbApp.conventionCollective.home.createOrEditLabel">
              Create or edit a ConventionCollective
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="convention-collective-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('adpaievfbApp.conventionCollective.idcc')}
                id="convention-collective-idcc"
                name="idcc"
                data-cy="idcc"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('adpaievfbApp.conventionCollective.nom')}
                id="convention-collective-nom"
                name="nom"
                data-cy="nom"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('adpaievfbApp.conventionCollective.position')}
                id="convention-collective-position"
                name="position"
                data-cy="position"
                type="text"
              />
              <ValidatedField
                label={translate('adpaievfbApp.conventionCollective.coefficient')}
                id="convention-collective-coefficient"
                name="coefficient"
                data-cy="coefficient"
                type="text"
              />
              <ValidatedField
                label={translate('adpaievfbApp.conventionCollective.valeurPoint')}
                id="convention-collective-valeurPoint"
                name="valeurPoint"
                data-cy="valeurPoint"
                type="text"
              />
              <ValidatedField
                label={translate('adpaievfbApp.conventionCollective.baseFixe')}
                id="convention-collective-baseFixe"
                name="baseFixe"
                data-cy="baseFixe"
                type="text"
              />
              <ValidatedField
                label={translate('adpaievfbApp.conventionCollective.salaireMinimal')}
                id="convention-collective-salaireMinimal"
                name="salaireMinimal"
                data-cy="salaireMinimal"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/convention-collective" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ConventionCollectiveUpdate;
