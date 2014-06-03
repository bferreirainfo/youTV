package business.usuario;

import utils.Utils;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoStatistics;

public class VideoView {
    private String id;
    private String title;
    private String views;
    private String videoType;
    private String commentCount;
    private String dislikeCount;
    private String likeCount;
    private String thumbnailUrl;
    private String uploadDate;
    private String duration;

    public VideoView(VimeoVideo vimeoVideo) {
        id = vimeoVideo.getId();
        title = vimeoVideo.getTitle();
        views = Utils.formatNumberWithDots(vimeoVideo.getNumberOfPlays());
        likeCount = vimeoVideo.getLikeCount();
        dislikeCount = "n/a";
        uploadDate = vimeoVideo.getUploadDate();
        duration = vimeoVideo.getDuration();
        thumbnailUrl = vimeoVideo.getThumbnails().getMediumThumbail().getContent();
        videoType = "vimeo";
    }

    public VideoView(Video video) {
        VideoStatistics videoStatistics = video.getStatistics();
        id = video.getId();
        views = Utils.formatNumberWithDots(videoStatistics.getViewCount().toString());
        dislikeCount = videoStatistics.getDislikeCount().toString();
        likeCount = videoStatistics.getLikeCount().toString();
        thumbnailUrl = video.getSnippet().getThumbnails().getDefault().getUrl();
        duration = video.getContentDetails().getDuration();
        String date = "";
        DateTime publishedAt = video.getSnippet().getPublishedAt();
        for (String datePart : publishedAt.toString().substring(0, 10).split("-")) {
            date = datePart + (date != "" ? "/" + date : "");
        }
        uploadDate = date;

        title = video.getSnippet().getTitle();
    }

    public boolean isVimeo() {
        return "vimeo".equals(videoType);
    }

    public boolean isYoutube() {
        return "youtube".equals(videoType);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(String dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setThumbnailUrl(String url) {
        thumbnailUrl = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
