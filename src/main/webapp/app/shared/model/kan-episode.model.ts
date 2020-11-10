import { IKanSeries } from 'app/shared/model/kan-series.model';

export interface IKanEpisode {
  id?: number;
  episodeId?: number;
  seriesId?: number;
  title?: string;
  text?: string;
  img?: string;
  videoSrc?: string;
  seriesId?: IKanSeries;
}

export const defaultValue: Readonly<IKanEpisode> = {};
