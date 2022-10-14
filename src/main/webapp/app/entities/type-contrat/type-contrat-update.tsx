import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICotisation } from 'app/shared/model/cotisation.model';
import { getEntities as getCotisations } from 'app/entities/cotisation/cotisation.reducer';
import { ITypeContrat } from 'app/shared/model/type-contrat.model';
import { TypeDeContrat } from 'app/shared/model/enumerations/type-de-contrat.model';
import { getEntity, updateEntity, createEntity, reset } from './type-contrat.reducer';

export const TypeContratUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cotisations = useAppSelector(state => state.cotisation.entities);
  const typeContratEntity = useAppSelector(state => state.typeContrat.entity);
  const loading = useAppSelector(state => state.typeContrat.loading);
  const updating = useAppSelector(state => state.typeContrat.updating);
  const updateSuccess = useAppSelector(state => state.typeContrat.updateSuccess);
  const typeDeContratValues = Object.keys(TypeDeContrat);

  const handleClose = () => {
    navigate('/type-contrat');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCotisations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...typeContratEntity,
      ...values,
      cotisations: mapIdList(values.cotisations),
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
          typeContrat: 'Stage',
          ...typeContratEntity,
          cotisations: typeContratEntity?.cotisations?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="adpaievfbApp.typeContrat.home.createOrEditLabel" data-cy="TypeContratCreateUpdateHeading">
            <Translate contentKey="adpaievfbApp.typeContrat.home.createOrEditLabel">Create or edit a TypeContrat</Translate>
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
                  id="type-contrat-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('adpaievfbApp.typeContrat.typeContrat')}
                id="type-contrat-typeContrat"
                name="typeContrat"
                data-cy="typeContrat"
                type="select"
              >
                {typeDeContratValues.map(typeDeContrat => (
                  <option value={typeDeContrat} key={typeDeContrat}>
                    {translate('adpaievfbApp.TypeDeContrat.' + typeDeContrat)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('adpaievfbApp.typeContrat.cotisation')}
                id="type-contrat-cotisation"
                data-cy="cotisation"
                type="select"
                multiple
                name="cotisations"
              >
                <option value="" key="0" />
                {cotisations
                  ? cotisations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/type-contrat" replace color="info">
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

export default TypeContratUpdate;
