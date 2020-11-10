import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IKanSeries } from 'app/shared/model/kan-series.model';
import { getEntities as getKanSeries } from 'app/entities/kan-series/kan-series.reducer';
import { getEntity, updateEntity, createEntity, reset } from './kan-episode.reducer';
import { IKanEpisode } from 'app/shared/model/kan-episode.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IKanEpisodeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const KanEpisodeUpdate = (props: IKanEpisodeUpdateProps) => {
  const [seriesIdId, setSeriesIdId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { kanEpisodeEntity, kanSeries, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/kan-episode');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getKanSeries();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...kanEpisodeEntity,
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
          <h2 id="israelVodServerApp.kanEpisode.home.createOrEditLabel">
            <Translate contentKey="israelVodServerApp.kanEpisode.home.createOrEditLabel">Create or edit a KanEpisode</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : kanEpisodeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="kan-episode-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="kan-episode-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="episodeIdLabel" for="kan-episode-episodeId">
                  <Translate contentKey="israelVodServerApp.kanEpisode.episodeId">Episode Id</Translate>
                </Label>
                <AvField id="kan-episode-episodeId" type="string" className="form-control" name="episodeId" />
              </AvGroup>
              <AvGroup>
                <Label id="seriesIdLabel" for="kan-episode-seriesId">
                  <Translate contentKey="israelVodServerApp.kanEpisode.seriesId">Series Id</Translate>
                </Label>
                <AvField id="kan-episode-seriesId" type="string" className="form-control" name="seriesId" />
              </AvGroup>
              <AvGroup>
                <Label id="titleLabel" for="kan-episode-title">
                  <Translate contentKey="israelVodServerApp.kanEpisode.title">Title</Translate>
                </Label>
                <AvField id="kan-episode-title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="textLabel" for="kan-episode-text">
                  <Translate contentKey="israelVodServerApp.kanEpisode.text">Text</Translate>
                </Label>
                <AvField id="kan-episode-text" type="text" name="text" />
              </AvGroup>
              <AvGroup>
                <Label id="imgLabel" for="kan-episode-img">
                  <Translate contentKey="israelVodServerApp.kanEpisode.img">Img</Translate>
                </Label>
                <AvField id="kan-episode-img" type="text" name="img" />
              </AvGroup>
              <AvGroup>
                <Label id="videoSrcLabel" for="kan-episode-videoSrc">
                  <Translate contentKey="israelVodServerApp.kanEpisode.videoSrc">Video Src</Translate>
                </Label>
                <AvField id="kan-episode-videoSrc" type="text" name="videoSrc" />
              </AvGroup>
              <AvGroup>
                <Label for="kan-episode-seriesId">
                  <Translate contentKey="israelVodServerApp.kanEpisode.seriesId">Series Id</Translate>
                </Label>
                <AvInput id="kan-episode-seriesId" type="select" className="form-control" name="seriesId.id">
                  <option value="" key="0" />
                  {kanSeries
                    ? kanSeries.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/kan-episode" replace color="info">
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
  kanSeries: storeState.kanSeries.entities,
  kanEpisodeEntity: storeState.kanEpisode.entity,
  loading: storeState.kanEpisode.loading,
  updating: storeState.kanEpisode.updating,
  updateSuccess: storeState.kanEpisode.updateSuccess,
});

const mapDispatchToProps = {
  getKanSeries,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(KanEpisodeUpdate);
