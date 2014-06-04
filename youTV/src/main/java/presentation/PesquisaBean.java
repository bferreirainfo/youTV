package presentation;

import java.util.List;

import javax.faces.bean.ManagedBean;

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

    public void setOperationToDefault() {
        operation = Operation.search;
        clearValues();
    }

    //This method is need until the aplication scope is fixed
    private void clearValues() {
        searchTerm = null;
        //for develop porpuse dont clear carrousel entities
        //        youTubeVideosSearchResult.clear();
        //        vimeoVideosSearchResult.clear();
    }

    public void loadVimeoVideo() {
        if (vimeoVideosSearchResult == null) {
            videoView = VimeoService.getVideoById(videoViewId);
        } else {
            localLoad(vimeoVideosSearchResult);
        }
        operation = Operation.playVimeo;
        clearValues();
    }

    public void loadYoutubeVideo() {
        if (youTubeVideosSearchResult == null) {
            videoView = YoutubeService.getVideoById(videoViewId);
        } else {
            localLoad(youTubeVideosSearchResult);
        }
        operation = Operation.playYoutube;
        clearValues();
    }

    private void localLoad(List<VideoView> localVideos) {
        for (VideoView videoView : localVideos) {
            if (videoView.getId().equals(videoViewId)) {
                this.videoView = videoView;
            }
        }
    }

    public void pesquisar() {
        try {
            youTubeVideosSearchResult = YoutubeService.searchVideos(searchTerm);
            vimeoVideosSearchResult = VimeoService.searchVideos(searchTerm);
            System.out.println("youtube: " + youTubeVideosSearchResult.size());
            System.out.println("vimeo: " + vimeoVideosSearchResult.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

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
