package business.usuario;

import java.io.IOException;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoStatistics;
import com.google.common.collect.Lists;

public class VideoView {
    private String id;
    private String title;
    private String views;
    private String videoType;
    private String commentCount;
    private String dislikeCount;
    private String likeCount;

    public VideoView(VimeoVideo vimeoVideo) {
        id = vimeoVideo.getId();
        title = vimeoVideo.getTitle();
        views = vimeoVideo.getNumberOfPlays();
        likeCount = vimeoVideo.getNumberOfLikes();
        dislikeCount = "não disponível";
        videoType = "vimeo";
    }

    public VideoView(String youtubeVideoId) throws IOException {
        com.google.api.services.youtube.YouTube.Videos.List videosList =
                loadVideoStatistics(youtubeVideoId);

        Video video = videosList.execute().getItems().get(0);
        VideoStatistics videoStatistics = video.getStatistics();

        id = youtubeVideoId;
        title = video.getSnippet().getTitle();
        views = videoStatistics.getViewCount().toString();
        dislikeCount = videoStatistics.getDislikeCount().toString();
        likeCount = videoStatistics.getLikeCount().toString();
        videoType = "youtube";
    }

    private com.google.api.services.youtube.YouTube.Videos.List loadVideoStatistics(
            String youtubeVideoId) throws IOException {
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
        Credential credential = Auth.authorize(scopes, "uploadvideo");

        // This object is used to make YouTube Data API requests.
        com.google.api.services.youtube.YouTube youtube =
                new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                        .setApplicationName("youtv1988").build();

        com.google.api.services.youtube.YouTube.Videos.List videosList =
                youtube.videos().list("statistics,snippet").setId(youtubeVideoId);
        return videosList;
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
}
