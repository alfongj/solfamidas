package es.solfamidas.elmundo.common.datasource;

import java.util.ArrayList;

public interface FlickrDataSource {

    /**
     * Search by tag on flick, returning urls of matching photos.
     *
     * @param tag            text to search.
     * @param numberOfPhotos number of photo urls to return. Max value 500.
     * @param callback       result event.
     */
    public void getFlickrImagesByTag(String tag, int numberOfPhotos, FlickrCallback callback);

    public interface FlickrCallback {

        public void flickrSuccess(ArrayList<String> imageUrls);

        public void flickrError(String errorMsg);

    }
}
