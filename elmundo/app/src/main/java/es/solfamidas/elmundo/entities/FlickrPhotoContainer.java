package es.solfamidas.elmundo.entities;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class FlickrPhotoContainer {

    @Expose
    private Integer page;
    @Expose
    private Integer pages;
    @Expose
    private Integer perpage;
    @Expose
    private String total;
    @Expose
    private List<FlickrPhoto> photo = new ArrayList<FlickrPhoto>();

    /**
     * @return The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return The pages
     */
    public Integer getPages() {
        return pages;
    }

    /**
     * @param pages The pages
     */
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    /**
     * @return The perpage
     */
    public Integer getPerpage() {
        return perpage;
    }

    /**
     * @param perpage The perpage
     */
    public void setPerpage(Integer perpage) {
        this.perpage = perpage;
    }

    /**
     * @return The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return The photo
     */
    public List<FlickrPhoto> getPhoto() {
        return photo;
    }

    /**
     * @param flickrPhoto The photo
     */
    public void setPhoto(List<FlickrPhoto> flickrPhoto) {
        this.photo = flickrPhoto;
    }

}
