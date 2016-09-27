package nl.md.hva.vonk.seriesapp;

/**
 * Created by Vonk on 27/09/16.
 */

public class Episode {
    
    private long id;
    private String episode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return episode;
    }
}
