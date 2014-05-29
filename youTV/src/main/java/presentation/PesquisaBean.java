package presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import business.usuario.VideoView;
import business.usuario.VimeoService;
import business.usuario.VimeoVideo;
import business.usuario.VimeoVideoSearchResult;

import com.google.api.services.samples.youtube.cmdline.data.Search;
import com.google.api.services.youtube.model.SearchResult;

@Controller
@ManagedBean
@Scope("session")
public class PesquisaBean {
    private String searchTerm;
    private List<SearchResult> resultadoPesquisa;
    private VimeoVideoSearchResult searchVideos;
    private String youtubeVideoId;
    private String vimeoVideoId;
    private VimeoVideo vimeoVideo;
    private SearchResult youtubeVideo;
    private VideoView videoView;
    private Operation operation;

    public void resolveUrlAction(ComponentSystemEvent event) throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc.getPartialViewContext().isAjaxRequest()) {
            return; // Skip ajax requests.
        }
        Map<String, String> requestParameterMap = fc.getExternalContext().getRequestParameterMap();

        boolean isUrlHeadertEmpty = requestParameterMap.isEmpty();
        if (isUrlHeadertEmpty) {
            operation = Operation.search;
        } else if (requestParameterMap.containsKey("youtube")) {
            youtubeVideoId = requestParameterMap.get("youtube");
            carregarDadosYoutube();
        } else if (requestParameterMap.containsKey("vimeo")) {
            vimeoVideoId = requestParameterMap.get("vimeo");
            carregarDadosVimeo();
        } else {
            throw new IOException("initial operation not defined");
        }
        clearValues();
    }

    public void onLoad() {
        System.out.println("onload callled");
    }

    private void clearValues() {
        searchTerm = null;
        youtubeVideoId = null;
        vimeoVideoId = null;
    }

    public void carregarDadosVimeo() {
        VimeoVideo video =
                searchVideos == null ? VimeoService.getVideoById(vimeoVideoId)
                        : recuperarVideoLocal();
        videoView = new VideoView(video);
        operation = Operation.playVimeo;
    }

    private VimeoVideo recuperarVideoLocal() {
        for (VimeoVideo video : searchVideos.getVideos().getVideos()) {
            if (video.getId().equals(vimeoVideoId)) {
                return video;
            }
        }
        return null;
    }

    public void carregarDadosYoutube() throws IOException {
        videoView = new VideoView(youtubeVideoId);
        operation = Operation.playYoutube;
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

    public void setYoutubeVideoId(String youtubeVideoId) throws IOException {
        this.youtubeVideoId = youtubeVideoId;
        carregarDadosYoutube();
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

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
