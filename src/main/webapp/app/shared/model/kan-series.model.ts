import { IKanEpisode } from 'app/shared/model/kan-episode.model';

export interface IKanSeries {
  id?: number;
  seriesId?: number;
  title?: string;
  text?: string;
  img?: string;
  webPage?: string;
  kanEpisodes?: IKanEpisode[];
}

export const defaultValue: Readonly<IKanSeries> = {};
