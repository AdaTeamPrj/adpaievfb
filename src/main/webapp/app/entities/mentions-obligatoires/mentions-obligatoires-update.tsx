import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFicheDePaie } from 'app/shared/model/fiche-de-paie.model';
import { getEntities as getFicheDePaies } from 'app/entities/fiche-de-paie/fiche-de-paie.reducer';
import { IMentionsObligatoires } from 'app/shared/model/mentions-obligatoires.model';
import { getEntity, updateEntity, createEntity, reset } from './mentions-obligatoires.reducer';

export const MentionsObligatoiresUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ficheDePaies = useAppSelector(state => state.ficheDePaie.entities);
  const mentionsObligatoiresEntity = useAppSelector(state => state.mentionsObligatoires.entity);
  const loading = useAppSelector(state => state.mentionsObligatoires.loading);
  const updating = useAppSelector(state => state.mentionsObligatoires.updating);
  const updateSuccess = useAppSelector(state => state.mentionsObligatoires.updateSuccess);

  const handleClose = () => {
    navigate('/mentions-obligatoires');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFicheDePaies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...mentionsObligatoiresEntity,
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
          ...mentionsObligatoiresEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="adpaievfbApp.mentionsObligatoires.home.createOrEditLabel" data-cy="MentionsObligatoiresCreateUpdateHeading">
            <Translate contentKey="adpaievfbApp.mentionsObligatoires.home.createOrEditLabel">
              Create or edit a MentionsObligatoires
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
                  id="mentions-obligatoires-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('adpaievfbApp.mentionsObligatoires.mention')}
                id="mentions-obligatoires-mention"
                name="mention"
                data-cy="mention"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('adpaievfbApp.mentionsObligatoires.description')}
                id="mentions-obligatoires-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/mentions-obligatoires" replace color="info">
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

export default MentionsObligatoiresUpdate;
