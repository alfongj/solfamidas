
package es.solfamidas.elmundo.entities;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Article {

    @Expose
    private String title;
    @Expose
    private String description;
    @Expose
    private String summary;
    @Expose
    private String date;
    @Expose
    private String pubdate;
    @Expose
    private String link;
    @Expose
    private String guid;
    @Expose
    private String author;
    @Expose
    private List<String> categories = new ArrayList<String>();
    @Expose
    private ArticleImage image;

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The pubdate
     */
    public String getPubdate() {
        return pubdate;
    }

    /**
     * 
     * @param pubdate
     *     The pubdate
     */
    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    /**
     * 
     * @return
     *     The link
     */
    public String getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The guid
     */
    public String getGuid() {
        return guid;
    }

    /**
     * 
     * @param guid
     *     The guid
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * 
     * @return
     *     The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The categories
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * 
     * @param categories
     *     The categories
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    /**
     * 
     * @return
     *     The image
     */
    public ArticleImage getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(ArticleImage image) {
        this.image = image;
    }

}
