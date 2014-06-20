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
    private String likeCount;
    @SerializedName(value = "number_of_comments")
    private String numberOfComments;
    @SerializedName(value = "duration")
    private String duration;
    @SerializedName(value = "description")
    private String description;
    @SerializedName(value = "thumbnails")
    private VimeoThumbnails thumbnails;
    @SerializedName(value = "owner")
    private Owner owner;

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
        String date = "";
        for (String datePart : uploadDate.substring(0, 10).split("-")) {
            date = datePart + (date != "" ? "/" + date : "");
        }
        return date;
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

    public String getNumberOfPlays() {
        return numberOfPlays;
    }

    public void setNumberOfPlays(String numberOfPlays) {
        this.numberOfPlays = numberOfPlays;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String numberOfLikes) {
        this.likeCount = numberOfLikes;
    }

    public String getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(String numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

}
