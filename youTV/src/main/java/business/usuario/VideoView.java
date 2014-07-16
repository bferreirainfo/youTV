package business.usuario;

import java.math.BigInteger;
import java.util.List;

import utils.Utils;

import com.google.api.services.youtube.model.PlaylistItem;
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
    private String channelTitle;

    public VideoView(VimeoVideo vimeoVideo) {
        //https://developer.vimeo.com/apis/advanced/methods
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
        channelTitle = vimeoVideo.getOwner().getChannelTitle();
    }

    public VideoView(Video video) {
        //https://developer.vimeo.com/apis/advanced/methods/vimeo.videos.search
        videoType = VideoTypeEnum.youtube;
        id = video.getId();
        description = video.getSnippet().getDescription();
        channelTitle = video.getSnippet().getChannelTitle();
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

    public VideoView(PlaylistItem playlistItem) {
        videoType = VideoTypeEnum.youtube;
         id = playlistItem.getId();
        description = playlistItem.getSnippet().getDescription();
        channelTitle = playlistItem.getSnippet().getChannelTitle();
        VideoStatistics videoStatistics = playlistItem.getStatistics();
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

        thumbnailUrl = playlistItem.getSnippet().getThumbnails().getMedium().getUrl();
        duration = playlistItem.getContentDetails().getDuration();
        uploadDate = Utils.obtainFormatYoutubeVideoDate(playlistItem);
        title = playlistItem.getSnippet().getTitle();
         */

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

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public boolean isVimeoVideo() {
        return VideoTypeEnum.vimeo.equals(videoType);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VideoView other = (VideoView) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
