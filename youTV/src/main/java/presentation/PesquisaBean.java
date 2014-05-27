package presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import business.usuario.VideoView;
import business.usuario.VimeoService;
import business.usuario.VimeoVideo;
import business.usuario.VimeoVideoSearchResult;

import com.google.api.services.samples.youtube.cmdline.data.Search;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.SearchResult;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PesquisaBean {
    private String searchTerm;
    private List<SearchResult> resultadoPesquisa;
    private List<PlaylistItem> myUploads;
    private VimeoVideoSearchResult searchVideos;
    private String youtubeVideoId;
    private String vimeoVideoId;
    private VimeoVideo vimeoVideo;
    private SearchResult youtubeVideo;
    private VideoView videoView;
    private Operation operation;

    public PesquisaBean() {
        operation = Operation.searching;
    }

    public void carregarDadosVimeo() {
        System.out.println("carregarDadosVimeo");
        for (VimeoVideo video : searchVideos.getVideos().getVideos()) {
            if (video.getId().equals(vimeoVideoId)) {
                //vericicar se passar o id é suficiente
                videoView = new VideoView(video);
            }
        }
        operation = Operation.watching;
    }

    public void carregarDadosYoutube() throws IOException {
        System.out.println("carregarDadosYoutube");
        videoView = new VideoView(youtubeVideoId);
        operation = Operation.watching;
    }

    public void pesquisar() {
        resultadoPesquisa = Search.pesquisar(searchTerm);
        searchVideos = VimeoService.searchVideos(searchTerm);
        List<SearchResult> aux = new ArrayList<SearchResult>();
        for (SearchResult item : resultadoPesquisa) {
            if ("youtube#video".equals(item.getId().getKind())) {
                aux.add(item);
            }
        }
        resultadoPesquisa = aux;
    }

    //    public void onTabChange(TabChangeEvent event) {
    //        if ("tab3".equals(event.getTab().getId())) {
    //            obtainMyUploads();
    //        }
    //    }

    //    public void obtainMyUploads() {
    //        myUploads = MyUploads.myUploads();
    //    }
    //
    //    public List<PlaylistItem> getMyUploads() {
    //        return myUploads;
    //    }
    //
    //    public void handleFileUpload(FileUploadEvent event) {
    //        FacesMessage msg =
    //                new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
    //        //        UploadVideo.upload(event.getFile());
    //        FacesContext.getCurrentInstance().addMessage(null, msg);
    //    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String termoPesquisa) {
        this.searchTerm = termoPesquisa;
    }

    public List<SearchResult> getResultadoPesquisa() {
        return resultadoPesquisa;
    }

    public void setResultadoPesquisa(List<SearchResult> resultadoPesquisa) {
        this.resultadoPesquisa = resultadoPesquisa;
    }

    public VimeoVideoSearchResult getSearchVideos() {
        return searchVideos;
    }

    public void setSearchVideos(VimeoVideoSearchResult searchVideos) {
        this.searchVideos = searchVideos;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public String getVimeoVideoId() {
        return vimeoVideoId;
    }

    public void setVimeoVideoId(String vimeoVideoId) {
        this.vimeoVideoId = vimeoVideoId;
    }

    public VimeoVideo getVimeoVideo() {
        return vimeoVideo;
    }

    public void setVimeoVideo(VimeoVideo vimeoVideo) {
        this.vimeoVideo = vimeoVideo;
    }

    public VideoView getVideoView() {
        return videoView;
    }

    public void setVideoView(VideoView videoView) {
        this.videoView = videoView;
    }

    public SearchResult getYoutubeVideo() {
        return youtubeVideo;
    }

    public void setYoutubeVideo(SearchResult youtubeVideo) {
        this.youtubeVideo = youtubeVideo;
    }

    public void setMyUploads(List<PlaylistItem> myUploads) {
        this.myUploads = myUploads;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
