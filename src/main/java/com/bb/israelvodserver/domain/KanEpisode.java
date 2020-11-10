package com.bb.israelvodserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A KanEpisode.
 */
@Entity
@Table(name = "kan_episode")
public class KanEpisode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "episode_id")
    private Integer episodeId;

    @Column(name = "series_id")
    private Integer seriesId;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "img")
    private String img;

    @Column(name = "video_src")
    private String videoSrc;

    @ManyToOne
    @JsonIgnoreProperties(value = "kanEpisodes", allowSetters = true)
    private KanSeries seriesId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEpisodeId() {
        return episodeId;
    }

    public KanEpisode episodeId(Integer episodeId) {
        this.episodeId = episodeId;
        return this;
    }

    public void setEpisodeId(Integer episodeId) {
        this.episodeId = episodeId;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public KanEpisode seriesId(Integer seriesId) {
        this.seriesId = seriesId;
        return this;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public String getTitle() {
        return title;
    }

    public KanEpisode title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public KanEpisode text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public KanEpisode img(String img) {
        this.img = img;
        return this;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getVideoSrc() {
        return videoSrc;
    }

    public KanEpisode videoSrc(String videoSrc) {
        this.videoSrc = videoSrc;
        return this;
    }

    public void setVideoSrc(String videoSrc) {
        this.videoSrc = videoSrc;
    }

    public KanSeries getSeriesId() {
        return seriesId;
    }

    public KanEpisode seriesId(KanSeries kanSeries) {
        this.seriesId = kanSeries;
        return this;
    }

    public void setSeriesId(KanSeries kanSeries) {
        this.seriesId = kanSeries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KanEpisode)) {
            return false;
        }
        return id != null && id.equals(((KanEpisode) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KanEpisode{" +
            "id=" + getId() +
            ", episodeId=" + getEpisodeId() +
            ", seriesId=" + getSeriesId() +
            ", title='" + getTitle() + "'" +
            ", text='" + getText() + "'" +
            ", img='" + getImg() + "'" +
            ", videoSrc='" + getVideoSrc() + "'" +
            "}";
    }
}
