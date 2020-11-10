import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IKanSeries, defaultValue } from 'app/shared/model/kan-series.model';

export const ACTION_TYPES = {
  FETCH_KANSERIES_LIST: 'kanSeries/FETCH_KANSERIES_LIST',
  FETCH_KANSERIES: 'kanSeries/FETCH_KANSERIES',
  CREATE_KANSERIES: 'kanSeries/CREATE_KANSERIES',
  UPDATE_KANSERIES: 'kanSeries/UPDATE_KANSERIES',
  DELETE_KANSERIES: 'kanSeries/DELETE_KANSERIES',
  RESET: 'kanSeries/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IKanSeries>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type KanSeriesState = Readonly<typeof initialState>;

// Reducer

export default (state: KanSeriesState = initialState, action): KanSeriesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_KANSERIES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_KANSERIES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_KANSERIES):
    case REQUEST(ACTION_TYPES.UPDATE_KANSERIES):
    case REQUEST(ACTION_TYPES.DELETE_KANSERIES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_KANSERIES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_KANSERIES):
    case FAILURE(ACTION_TYPES.CREATE_KANSERIES):
    case FAILURE(ACTION_TYPES.UPDATE_KANSERIES):
    case FAILURE(ACTION_TYPES.DELETE_KANSERIES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_KANSERIES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_KANSERIES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_KANSERIES):
    case SUCCESS(ACTION_TYPES.UPDATE_KANSERIES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_KANSERIES):
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

const apiUrl = 'api/kan-series';

// Actions

export const getEntities: ICrudGetAllAction<IKanSeries> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_KANSERIES_LIST,
    payload: axios.get<IKanSeries>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IKanSeries> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_KANSERIES,
    payload: axios.get<IKanSeries>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IKanSeries> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_KANSERIES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IKanSeries> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_KANSERIES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IKanSeries> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_KANSERIES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
