import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IKanEpisode, defaultValue } from 'app/shared/model/kan-episode.model';

export const ACTION_TYPES = {
  FETCH_KANEPISODE_LIST: 'kanEpisode/FETCH_KANEPISODE_LIST',
  FETCH_KANEPISODE: 'kanEpisode/FETCH_KANEPISODE',
  CREATE_KANEPISODE: 'kanEpisode/CREATE_KANEPISODE',
  UPDATE_KANEPISODE: 'kanEpisode/UPDATE_KANEPISODE',
  DELETE_KANEPISODE: 'kanEpisode/DELETE_KANEPISODE',
  RESET: 'kanEpisode/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IKanEpisode>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type KanEpisodeState = Readonly<typeof initialState>;

// Reducer

export default (state: KanEpisodeState = initialState, action): KanEpisodeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_KANEPISODE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_KANEPISODE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_KANEPISODE):
    case REQUEST(ACTION_TYPES.UPDATE_KANEPISODE):
    case REQUEST(ACTION_TYPES.DELETE_KANEPISODE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_KANEPISODE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_KANEPISODE):
    case FAILURE(ACTION_TYPES.CREATE_KANEPISODE):
    case FAILURE(ACTION_TYPES.UPDATE_KANEPISODE):
    case FAILURE(ACTION_TYPES.DELETE_KANEPISODE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_KANEPISODE_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_KANEPISODE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_KANEPISODE):
    case SUCCESS(ACTION_TYPES.UPDATE_KANEPISODE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_KANEPISODE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/kan-episodes';

// Actions

export const getEntities: ICrudGetAllAction<IKanEpisode> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_KANEPISODE_LIST,
    payload: axios.get<IKanEpisode>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IKanEpisode> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_KANEPISODE,
    payload: axios.get<IKanEpisode>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IKanEpisode> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_KANEPISODE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IKanEpisode> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_KANEPISODE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IKanEpisode> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_KANEPISODE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
