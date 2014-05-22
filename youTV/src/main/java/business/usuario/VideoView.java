package business.usuario;

import com.google.api.services.youtube.model.SearchResult;

public class VideoView {
    private String title;
    private String views;

    public VideoView(VimeoVideo vimeoVideo) {
        title = vimeoVideo.getTitle();
        views = vimeoVideo.getNumberOfPlays();
    }

    public VideoView(SearchResult youtubeVideo) {
        title = youtubeVideo.getSnippet().getTitle();
    }
}
