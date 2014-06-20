package business.usuario;

import com.google.gson.annotations.SerializedName;

public class VimeoVideoSearchResult {
    @SerializedName("generated_in")
    private String generatedIn;
    @SerializedName("stat")
    private String status;
    @SerializedName("videos")
    private VimeoVideos videos;
    @SerializedName("video")
    private VimeoVideo[] video;

    public void setGeneratedIn(String generatedIn) {
        this.generatedIn = generatedIn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isStatusOk() {
        return "ok".equals(status);
    }

    public VimeoVideos getVideos() {
        return videos;
    }

    public void setVideos(VimeoVideos videos) {
        this.videos = videos;
    }

    public String getGeneratedIn() {
        return generatedIn;
    }

    public VimeoVideo[] getVideo() {
        return video;
    }

    public void setVideo(VimeoVideo[] video) {
        this.video = video;
    }

}
