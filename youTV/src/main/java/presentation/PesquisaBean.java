package presentation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import business.usuario.VideoView;
import business.usuario.VimeoService;

import com.google.api.services.samples.youtube.cmdline.data.YoutubeService;

@Controller
@ManagedBean
@Scope("session")
public class PesquisaBean {
    //Search Term
    private String searchTerm;

    //Carrousel entities
    private List<VideoView> youTubeVideosSearchResult;
    private List<VideoView> vimeoVideosSearchResult;

    //VideoView attributes
    private String videoViewId;
    private Operation operation;
    private VideoView videoView;

    //Determine the action to do when the view is rendered
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
            videoViewId = requestParameterMap.get("youtube");
            loadYoutubeVideo();
        } else if (requestParameterMap.containsKey("vimeo")) {
            videoViewId = requestParameterMap.get("vimeo");
            loadVimeoVideo();
        } else {
            throw new IOException("initial operation not recognized.");
        }
        clearValues();
    }

    //This method is need until the aplication scope is fixed
    private void clearValues() {
        searchTerm = null;
        videoViewId = null;
        //for develop porpuse dont clear carrousel entities
        //        youTubeVideosSearchResult.clear();
        //        vimeoVideosSearchResult.clear();
    }

    public void loadVimeoVideo() {
        if (vimeoVideosSearchResult == null) {
            videoView = VimeoService.getVideoById(videoViewId);
        } else {
            carregarLocal(vimeoVideosSearchResult);
        }
        operation = Operation.playVimeo;
    }

    public void loadYoutubeVideo() {
        if (youTubeVideosSearchResult == null) {
            YoutubeService.getVideoById(videoViewId);
        } else {
            carregarLocal(youTubeVideosSearchResult);
        }
        operation = Operation.playYoutube;
    }

    private void carregarLocal(List<VideoView> localVideos) {
        for (VideoView videoView : localVideos) {
            if (videoView.getId().equals(videoViewId)) {
                this.videoView = videoView;
            }
        }
    }

    public void pesquisar() {
        youTubeVideosSearchResult = YoutubeService.searchVideos(searchTerm);
        vimeoVideosSearchResult = VimeoService.searchVideos(searchTerm);
        System.out.println("youtube: " + youTubeVideosSearchResult.size());
        System.out.println("vimeo: " + vimeoVideosSearchResult.size());
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

    public List<VideoView> getYouTubeVideosSearchResult() {
        return youTubeVideosSearchResult;
    }

    public void setYouTubeVideosSearchResult(List<VideoView> resultadoPesquisa) {
        this.youTubeVideosSearchResult = resultadoPesquisa;
    }

    public List<VideoView> getVimeoVideosSearchResult() {
        return vimeoVideosSearchResult;
    }

    public void setVimeoVideosSearchResult(List<VideoView> searchVideos) {
        this.vimeoVideosSearchResult = searchVideos;
    }

    public String getVideoViewId() {
        return videoViewId;
    }

    public void setVideoViewId(String vimeoVideoId) {
        this.videoViewId = vimeoVideoId;
    }

    public VideoView getVideoView() {
        return videoView;
    }

    public void setVideoView(VideoView videoView) {
        this.videoView = videoView;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
