import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './kan-series.reducer';
import { IKanSeries } from 'app/shared/model/kan-series.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IKanSeriesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const KanSeriesDetail = (props: IKanSeriesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { kanSeriesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="israelVodServerApp.kanSeries.detail.title">KanSeries</Translate> [<b>{kanSeriesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="seriesId">
              <Translate contentKey="israelVodServerApp.kanSeries.seriesId">Series Id</Translate>
            </span>
          </dt>
          <dd>{kanSeriesEntity.seriesId}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="israelVodServerApp.kanSeries.title">Title</Translate>
            </span>
          </dt>
          <dd>{kanSeriesEntity.title}</dd>
          <dt>
            <span id="text">
              <Translate contentKey="israelVodServerApp.kanSeries.text">Text</Translate>
            </span>
          </dt>
          <dd>{kanSeriesEntity.text}</dd>
          <dt>
            <span id="img">
              <Translate contentKey="israelVodServerApp.kanSeries.img">Img</Translate>
            </span>
          </dt>
          <dd>{kanSeriesEntity.img}</dd>
          <dt>
            <span id="webPage">
              <Translate contentKey="israelVodServerApp.kanSeries.webPage">Web Page</Translate>
            </span>
          </dt>
          <dd>{kanSeriesEntity.webPage}</dd>
        </dl>
        <Button tag={Link} to="/kan-series" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kan-series/${kanSeriesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ kanSeries }: IRootState) => ({
  kanSeriesEntity: kanSeries.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(KanSeriesDetail);
