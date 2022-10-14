import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBaseTauxCotisation } from 'app/shared/model/base-taux-cotisation.model';
import { getEntities as getBaseTauxCotisations } from 'app/entities/base-taux-cotisation/base-taux-cotisation.reducer';
import { IFicheDePaie } from 'app/shared/model/fiche-de-paie.model';
import { getEntities as getFicheDePaies } from 'app/entities/fiche-de-paie/fiche-de-paie.reducer';
import { ITypeContrat } from 'app/shared/model/type-contrat.model';
import { getEntities as getTypeContrats } from 'app/entities/type-contrat/type-contrat.reducer';
import { ICotisation } from 'app/shared/model/cotisation.model';
import { Categorie } from 'app/shared/model/enumerations/categorie.model';
import { getEntity, updateEntity, createEntity, reset } from './cotisation.reducer';

export const CotisationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const baseTauxCotisations = useAppSelector(state => state.baseTauxCotisation.entities);
  const ficheDePaies = useAppSelector(state => state.ficheDePaie.entities);
  const typeContrats = useAppSelector(state => state.typeContrat.entities);
  const cotisationEntity = useAppSelector(state => state.cotisation.entity);
  const loading = useAppSelector(state => state.cotisation.loading);
  const updating = useAppSelector(state => state.cotisation.updating);
  const updateSuccess = useAppSelector(state => state.cotisation.updateSuccess);
  const categorieValues = Object.keys(Categorie);

  const handleClose = () => {
    navigate('/cotisation');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBaseTauxCotisations({}));
    dispatch(getFicheDePaies({}));
    dispatch(getTypeContrats({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...cotisationEntity,
      ...values,
      baseTauxCotisation: baseTauxCotisations.find(it => it.id.toString() === values.baseTauxCotisation.toString()),
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
          famille: 'Sante',
          ...cotisationEntity,
          baseTauxCotisation: cotisationEntity?.baseTauxCotisation?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="adpaievfbApp.cotisation.home.createOrEditLabel" data-cy="CotisationCreateUpdateHeading">
            <Translate contentKey="adpaievfbApp.cotisation.home.createOrEditLabel">Create or edit a Cotisation</Translate>
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
                  id="cotisation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('adpaievfbApp.cotisation.name')}
                id="cotisation-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('adpaievfbApp.cotisation.famille')}
                id="cotisation-famille"
                name="famille"
                data-cy="famille"
                type="select"
              >
                {categorieValues.map(categorie => (
                  <option value={categorie} key={categorie}>
                    {translate('adpaievfbApp.Categorie.' + categorie)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('adpaievfbApp.cotisation.taux')}
                id="cotisation-taux"
                name="taux"
                data-cy="taux"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('adpaievfbApp.cotisation.startDate')}
                id="cotisation-startDate"
                name="startDate"
                data-cy="startDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('adpaievfbApp.cotisation.endDate')}
                id="cotisation-endDate"
                name="endDate"
                data-cy="endDate"
                type="date"
              />
              <ValidatedField
                label={translate('adpaievfbApp.cotisation.actuel')}
                id="cotisation-actuel"
                name="actuel"
                data-cy="actuel"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('adpaievfbApp.cotisation.multiplicateurMin')}
                id="cotisation-multiplicateurMin"
                name="multiplicateurMin"
                data-cy="multiplicateurMin"
                type="text"
              />
              <ValidatedField
                label={translate('adpaievfbApp.cotisation.multiplicateurMax')}
                id="cotisation-multiplicateurMax"
                name="multiplicateurMax"
                data-cy="multiplicateurMax"
                type="text"
              />
              <ValidatedField
                id="cotisation-baseTauxCotisation"
                name="baseTauxCotisation"
                data-cy="baseTauxCotisation"
                label={translate('adpaievfbApp.cotisation.baseTauxCotisation')}
                type="select"
              >
                <option value="" key="0" />
                {baseTauxCotisations
                  ? baseTauxCotisations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cotisation" replace color="info">
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

export default CotisationUpdate;
