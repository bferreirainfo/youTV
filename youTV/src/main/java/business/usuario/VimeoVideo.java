package business.usuario;

import com.google.gson.annotations.SerializedName;

public class VimeoVideo {
    @SerializedName(value = "embed_privacy")
    private String embedPrivacy;
    @SerializedName(value = "id")
    private String id;
    @SerializedName(value = "is_hd")
    private String isHd;
    @SerializedName(value = "license")
    private String license;
    @SerializedName(value = "modified_date")
    private String modifiedDate;
    @SerializedName(value = "privacy")
    private String privacy;
    @SerializedName(value = "title")
    private String title;
    @SerializedName(value = "upload_date")
    private String uploadDate;
    @SerializedName(value = "number_of_plays")
    private String numberOfPlays;
    @SerializedName(value = "number_of_likes")
    private String numberOfLikes;
    @SerializedName(value = "number_of_comments")
    private String numberOfComments;
    @SerializedName(value = "duration")
    private String duration;
    @SerializedName(value = "thumbnails")
    private VimeoThumbnails thumbnails;

    public VimeoThumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(VimeoThumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getEmbedPrivacy() {
        return embedPrivacy;
    }

    public void setEmbedPrivacy(String embedPrivacy) {
        this.embedPrivacy = embedPrivacy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsHd() {
        return isHd;
    }

    public void setIsHd(String isHd) {
        this.isHd = isHd;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
