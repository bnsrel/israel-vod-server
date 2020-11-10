import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './kan-episode.reducer';
import { IKanEpisode } from 'app/shared/model/kan-episode.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IKanEpisodeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const KanEpisodeDetail = (props: IKanEpisodeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { kanEpisodeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="israelVodServerApp.kanEpisode.detail.title">KanEpisode</Translate> [<b>{kanEpisodeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="episodeId">
              <Translate contentKey="israelVodServerApp.kanEpisode.episodeId">Episode Id</Translate>
            </span>
          </dt>
          <dd>{kanEpisodeEntity.episodeId}</dd>
          <dt>
            <span id="seriesId">
              <Translate contentKey="israelVodServerApp.kanEpisode.seriesId">Series Id</Translate>
            </span>
          </dt>
          <dd>{kanEpisodeEntity.seriesId}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="israelVodServerApp.kanEpisode.title">Title</Translate>
            </span>
          </dt>
          <dd>{kanEpisodeEntity.title}</dd>
          <dt>
            <span id="text">
              <Translate contentKey="israelVodServerApp.kanEpisode.text">Text</Translate>
            </span>
          </dt>
          <dd>{kanEpisodeEntity.text}</dd>
          <dt>
            <span id="img">
              <Translate contentKey="israelVodServerApp.kanEpisode.img">Img</Translate>
            </span>
          </dt>
          <dd>{kanEpisodeEntity.img}</dd>
          <dt>
            <span id="videoSrc">
              <Translate contentKey="israelVodServerApp.kanEpisode.videoSrc">Video Src</Translate>
            </span>
          </dt>
          <dd>{kanEpisodeEntity.videoSrc}</dd>
          <dt>
            <Translate contentKey="israelVodServerApp.kanEpisode.seriesId">Series Id</Translate>
          </dt>
          <dd>{kanEpisodeEntity.seriesId ? kanEpisodeEntity.seriesId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/kan-episode" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kan-episode/${kanEpisodeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ kanEpisode }: IRootState) => ({
  kanEpisodeEntity: kanEpisode.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(KanEpisodeDetail);
