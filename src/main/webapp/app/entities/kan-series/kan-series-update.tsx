import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './kan-series.reducer';
import { IKanSeries } from 'app/shared/model/kan-series.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IKanSeriesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const KanSeriesUpdate = (props: IKanSeriesUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { kanSeriesEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/kan-series' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...kanSeriesEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="israelVodServerApp.kanSeries.home.createOrEditLabel">
            <Translate contentKey="israelVodServerApp.kanSeries.home.createOrEditLabel">Create or edit a KanSeries</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : kanSeriesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="kan-series-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="kan-series-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="seriesIdLabel" for="kan-series-seriesId">
                  <Translate contentKey="israelVodServerApp.kanSeries.seriesId">Series Id</Translate>
                </Label>
                <AvField id="kan-series-seriesId" type="string" className="form-control" name="seriesId" />
              </AvGroup>
              <AvGroup>
                <Label id="titleLabel" for="kan-series-title">
                  <Translate contentKey="israelVodServerApp.kanSeries.title">Title</Translate>
                </Label>
                <AvField id="kan-series-title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="textLabel" for="kan-series-text">
                  <Translate contentKey="israelVodServerApp.kanSeries.text">Text</Translate>
                </Label>
                <AvField id="kan-series-text" type="text" name="text" />
              </AvGroup>
              <AvGroup>
                <Label id="imgLabel" for="kan-series-img">
                  <Translate contentKey="israelVodServerApp.kanSeries.img">Img</Translate>
                </Label>
                <AvField id="kan-series-img" type="text" name="img" />
              </AvGroup>
              <AvGroup>
                <Label id="webPageLabel" for="kan-series-webPage">
                  <Translate contentKey="israelVodServerApp.kanSeries.webPage">Web Page</Translate>
                </Label>
                <AvField id="kan-series-webPage" type="text" name="webPage" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/kan-series" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  kanSeriesEntity: storeState.kanSeries.entity,
  loading: storeState.kanSeries.loading,
  updating: storeState.kanSeries.updating,
  updateSuccess: storeState.kanSeries.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(KanSeriesUpdate);
