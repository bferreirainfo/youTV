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

    public void loadMiniplayerYoutubeVideo() {
        operation = Operation.playYoutube;
    }

    public void loadMiniplayerVimeoVideo() {
        operation = Operation.playVimeo;
    }

    public void loadVimeoVideo() {
        VideoView result = localLoad(vimeoVideosSearchResult);
        videoView = result == null ? VimeoService.getVideoById(videoViewId) : result;
        operation = Operation.playVimeo;
        clearValues();
    }

    public void loadYoutubeVideo() {
        VideoView result = localLoad(youTubeVideosSearchResult);
        videoView = result == null ? YoutubeService.getVideoById(videoViewId) : result;
        operation = Operation.playYoutube;
        clearValues();
    }

    private VideoView localLoad(List<VideoView> localVideos) {
        VideoView result = null;
        if (localVideos != null) {
            for (VideoView videoView : localVideos) {
                if (videoView.getId().equals(videoViewId)) {
                    result = videoView;
                    break;
                }
            }
        }
        return result;
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

    public void setVideoViewId(String videoViewId) {
        this.videoViewId = videoViewId;
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
