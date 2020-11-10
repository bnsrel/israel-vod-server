package com.bb.israelvodserver.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A KanSeries.
 */
@Entity
@Table(name = "kan_series")
public class KanSeries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "series_id")
    private Integer seriesId;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "img")
    private String img;

    @Column(name = "web_page")
    private String webPage;

    @OneToMany(mappedBy = "seriesId")
    private Set<KanEpisode> kanEpisodes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public KanSeries seriesId(Integer seriesId) {
        this.seriesId = seriesId;
        return this;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public String getTitle() {
        return title;
    }

    public KanSeries title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public KanSeries text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public KanSeries img(String img) {
        this.img = img;
        return this;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWebPage() {
        return webPage;
    }

    public KanSeries webPage(String webPage) {
        this.webPage = webPage;
        return this;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public Set<KanEpisode> getKanEpisodes() {
        return kanEpisodes;
    }

    public KanSeries kanEpisodes(Set<KanEpisode> kanEpisodes) {
        this.kanEpisodes = kanEpisodes;
        return this;
    }

    public KanSeries addKanEpisode(KanEpisode kanEpisode) {
        this.kanEpisodes.add(kanEpisode);
        kanEpisode.setSeriesId(this);
        return this;
    }

    public KanSeries removeKanEpisode(KanEpisode kanEpisode) {
        this.kanEpisodes.remove(kanEpisode);
        kanEpisode.setSeriesId(null);
        return this;
    }

    public void setKanEpisodes(Set<KanEpisode> kanEpisodes) {
        this.kanEpisodes = kanEpisodes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KanSeries)) {
            return false;
        }
        return id != null && id.equals(((KanSeries) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KanSeries{" +
            "id=" + getId() +
            ", seriesId=" + getSeriesId() +
            ", title='" + getTitle() + "'" +
            ", text='" + getText() + "'" +
            ", img='" + getImg() + "'" +
            ", webPage='" + getWebPage() + "'" +
            "}";
    }
}
