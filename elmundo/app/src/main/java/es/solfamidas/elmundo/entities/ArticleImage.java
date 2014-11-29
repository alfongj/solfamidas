package es.solfamidas.elmundo.entities;

import com.google.gson.annotations.Expose;

public class ArticleImage {

    @Expose
    private String url;
    @Expose
    private String medium;
    @Expose
    private String width;
    @Expose
    private String height;
    @Expose
    private String type;
    @Expose
    private String title;
    @Expose
    private String description;
    @Expose
    private ArticleThumbnail thumbnail;

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The medium
     */
    public String getMedium() {
        return medium;
    }

    /**
     * @param medium The medium
     */
    public void setMedium(String medium) {
        this.medium = medium;
    }

    /**
     * @return The width
     */
    public String getWidth() {
        return width;
    }

    /**
     * @param width The width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * @return The height
     */
    public String getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The thumbnail
     */
    public ArticleThumbnail getThumbnail() {
        return thumbnail;
    }

    /**
     * @param thumbnail The thumbnail
     */
    public void setThumbnail(ArticleThumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

}
