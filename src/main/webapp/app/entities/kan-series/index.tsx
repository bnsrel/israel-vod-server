import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import KanSeries from './kan-series';
import KanSeriesDetail from './kan-series-detail';
import KanSeriesUpdate from './kan-series-update';
import KanSeriesDeleteDialog from './kan-series-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={KanSeriesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={KanSeriesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KanSeriesDetail} />
      <ErrorBoundaryRoute path={match.url} component={KanSeries} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={KanSeriesDeleteDialog} />
  </>
);

export default Routes;
