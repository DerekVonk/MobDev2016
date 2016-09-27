package nl.md.hva.vonk.seriesapp;

/**
 * Holder class for Series object from datastore
 */
public class Series {

    // id of the series
    private long id;

    // description text of the series
    private String series;

    // episode object of the series
    private Episode episode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return series;
    }
}
