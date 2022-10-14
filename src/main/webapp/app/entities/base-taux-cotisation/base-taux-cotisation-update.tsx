import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBaseTauxCotisation } from 'app/shared/model/base-taux-cotisation.model';
import { getEntity, updateEntity, createEntity, reset } from './base-taux-cotisation.reducer';

export const BaseTauxCotisationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const baseTauxCotisationEntity = useAppSelector(state => state.baseTauxCotisation.entity);
  const loading = useAppSelector(state => state.baseTauxCotisation.loading);
  const updating = useAppSelector(state => state.baseTauxCotisation.updating);
  const updateSuccess = useAppSelector(state => state.baseTauxCotisation.updateSuccess);

  const handleClose = () => {
    navigate('/base-taux-cotisation');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...baseTauxCotisationEntity,
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
          ...baseTauxCotisationEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="adpaievfbApp.baseTauxCotisation.home.createOrEditLabel" data-cy="BaseTauxCotisationCreateUpdateHeading">
            <Translate contentKey="adpaievfbApp.baseTauxCotisation.home.createOrEditLabel">Create or edit a BaseTauxCotisation</Translate>
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
                  id="base-taux-cotisation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('adpaievfbApp.baseTauxCotisation.nom')}
                id="base-taux-cotisation-nom"
                name="nom"
                data-cy="nom"
                type="text"
              />
              <ValidatedField
                label={translate('adpaievfbApp.baseTauxCotisation.montant')}
                id="base-taux-cotisation-montant"
                name="montant"
                data-cy="montant"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/base-taux-cotisation" replace color="info">
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

export default BaseTauxCotisationUpdate;
