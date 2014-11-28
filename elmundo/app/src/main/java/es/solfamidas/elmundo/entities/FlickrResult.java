package es.solfamidas.elmundo.entities;

import com.google.gson.annotations.Expose;

public class FlickrResult {

    @Expose
    private FlickrPhotoContainer photos;
    @Expose
    private String stat;

    /**
     * @return The photos
     */
    public FlickrPhotoContainer getPhotos() {
        return photos;
    }

    /**
     * @param photos The photos
     */
    public void setPhotos(FlickrPhotoContainer photos) {
        this.photos = photos;
    }

    /**
     * @return The stat
     */
    public String getStat() {
        return stat;
    }

    /**
     * @param stat The stat
     */
    public void setStat(String stat) {
        this.stat = stat;
    }

}
