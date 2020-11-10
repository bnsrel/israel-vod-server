import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import KanEpisode from './kan-episode';
import KanEpisodeDetail from './kan-episode-detail';
import KanEpisodeUpdate from './kan-episode-update';
import KanEpisodeDeleteDialog from './kan-episode-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={KanEpisodeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={KanEpisodeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KanEpisodeDetail} />
      <ErrorBoundaryRoute path={match.url} component={KanEpisode} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={KanEpisodeDeleteDialog} />
  </>
);

export default Routes;
