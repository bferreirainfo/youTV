package business.usuario;

import java.math.BigInteger;
import java.util.List;

import utils.Utils;

import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoStatistics;

public class VideoView {
    private String id;
    private String title;
    private String views;
    private VideoTypeEnum videoType;
    private String commentCount;
    private String dislikeCount;
    private String likeCount;
    private String thumbnailUrl;
    private String uploadDate;
    private String duration;
    private String likesPercentage;
    private String dislikesPercentage;
    private String description;
    private List<VideoView> relatedVideos;

    public VideoView(VimeoVideo vimeoVideo) {
        videoType = VideoTypeEnum.vimeo;
        id = vimeoVideo.getId();
        title = vimeoVideo.getTitle();
        String numberOfPlays = vimeoVideo.getNumberOfPlays();
        views = Utils.formatNumberWithDots(numberOfPlays);
        likeCount = Utils.formatNumberWithDots(vimeoVideo.getLikeCount());
        dislikeCount = "n/a";
        uploadDate = vimeoVideo.getUploadDate();
        duration = vimeoVideo.getDuration();
        thumbnailUrl = vimeoVideo.getThumbnails().getMediumThumbail().getContent();
        description = vimeoVideo.getDescription();
    }

    public VideoView(Video video) {
        videoType = VideoTypeEnum.youtube;
        id = video.getId();
        description = video.getSnippet().getDescription();

        VideoStatistics videoStatistics = video.getStatistics();
        BigInteger viewCount = videoStatistics.getViewCount();
        BigInteger likesCount = videoStatistics.getLikeCount();
        BigInteger dislikesCount = videoStatistics.getDislikeCount();

        views = Utils.formatNumberWithDots(viewCount.toString());
        likeCount = Utils.formatNumberWithDots(likesCount.toString());
        dislikeCount = Utils.formatNumberWithDots(dislikesCount.toString());
        int[] likesAndDislikesPercentage =
                Utils.calculateLikesAndDislikesPercentage(likesCount.floatValue(),
                        dislikesCount.floatValue());
        likesPercentage = likesAndDislikesPercentage[0] + "%";
        dislikesPercentage = likesAndDislikesPercentage[1] + "%";

        thumbnailUrl = video.getSnippet().getThumbnails().getMedium().getUrl();
        duration = video.getContentDetails().getDuration();
        uploadDate = Utils.obtainFormatYoutubeVideoDate(video);
        title = video.getSnippet().getTitle();
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

    public String getLikesPercentage() {
        return likesPercentage;
    }

    public void setLikesPercentage(String likesPercentage) {
        this.likesPercentage = likesPercentage;
    }

    public String getDislikesPercentage() {
        return dislikesPercentage;
    }

    public void setDislikesPercentage(String dislikesPercentage) {
        this.dislikesPercentage = dislikesPercentage;
    }

    public String getDescription() {
        return description;
    }

    public List<VideoView> getRelatedVideos() {
        return relatedVideos;
    }

    public void setRelatedVideos(List<VideoView> relatedVideos) {
        this.relatedVideos = relatedVideos;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VideoTypeEnum getVideoType() {
        return videoType;
    }

    public void setVideoType(VideoTypeEnum videoType) {
        this.videoType = videoType;
    }

    public boolean isYoutubeVideo() {
        return VideoTypeEnum.youtube.equals(videoType);
    }

    public boolean isVimeoVideo() {
        return VideoTypeEnum.vimeo.equals(videoType);
    }

}
